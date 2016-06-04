/*
 * Copyright (c) 2015 Nova Ordis LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.novaordis.playground.wildfly.hornetq.jms;

import io.novaordis.playground.wildfly.hornetq.ftf.common.MessageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

public class SingleThreadedSender implements Runnable
{
    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(SingleThreadedSender.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private int id;
    private ConnectionManager cm;
    private AtomicInteger remainingToSend;
    private AtomicInteger messagesSent;
    private CyclicBarrier barrier;
    private Destination destination;
    // negative or zero means "don't sleep"
    private long sleepBetweenSendsMs;
    private BlockingQueue<MessageInfo> messageInfoQueue;

    // Constructors ----------------------------------------------------------------------------------------------------

    /**
     * @param messageInfoQueue the queue to write message information on, for external storage.
     */
    public SingleThreadedSender(int id, ConnectionManager cm, Destination destination,
                                long sleepBetweenSendsMs,
                                AtomicInteger remainingToSend, AtomicInteger messagesSent,
                                CyclicBarrier barrier,
                                BlockingQueue<MessageInfo> messageInfoQueue)
    {
        this.id = id;
        this.cm = cm;
        this.destination = destination;
        this.remainingToSend = remainingToSend;
        this.messagesSent = messagesSent;
        this.barrier = barrier;
        this.sleepBetweenSendsMs = sleepBetweenSendsMs;
        this.messageInfoQueue = messageInfoQueue;
    }

    // Runnable implementation -----------------------------------------------------------------------------------------

    public void run()
    {
        Connection connection = cm.getConnection();

        Session session;

        log.info(this + " using connection " + connection);

        int connectionRecreationAttempts = 0;

        outer: while(true) {

            try {

                session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                MessageProducer producer = session.createProducer(destination);

                while (true) {

                    int remaining = remainingToSend.getAndDecrement();

                    if (remaining <= 0) {
                        break outer;
                    }

                    //Message m = session.createTextMessage("test");
                    Message m = session.createTextMessage("test-" + System.currentTimeMillis());
                    producer.send(m);

                    //
                    // the current connection was successful in sending a message, reset the counter
                    //
                    connectionRecreationAttempts = 0;

                    MessageInfo mi = new MessageInfo();
                    mi.init(m);
                    messageInfoQueue.add(mi);

                    remaining--;
                    if (remaining < 0) {
                        remaining = 0;
                    }

                    if (sleepBetweenSendsMs > 0) {

                        log.info("sleeping " + (sleepBetweenSendsMs / 1000) +
                                " second(s) after sending the message, " + remaining + " message(s) remaining ...");
                        Thread.sleep(sleepBetweenSendsMs);
                    }

                    messagesSent.incrementAndGet();
                }
            }
            catch (Exception e) {

                log.info("thread " + Thread.currentThread().getName() + " failed: " + e);

                MessageInfo mi = new MessageInfo();
                mi.init(e);
                messageInfoQueue.add(mi);

                //
                // we use this exception as a hint to attempt to recreate the connection (via the ConnectionManager)
                //

                if (connectionRecreationAttempts == 3) {

                    log.info("maximum number of connection recreation attempts (" + connectionRecreationAttempts + ") reached, giving up ...");
                    break;

                }

                log.info("attempt number " + (++connectionRecreationAttempts) + " to recreate the connection ...");

                try {
                    connection = cm.recreateConnection();
                }
                catch(JMSException jmse) {

                    log.error("an attempt to recreate the connection fail, this means failover did not work", jmse);
                    break;
                }
            }
        }

        try {

            barrier.await();
        }
        catch (Exception e) {
            log.info("failed to wait on barrier: " + e);
        }

    }

    // Public ----------------------------------------------------------------------------------------------------------

    public String toString()
    {
        return "SingleThreadedRunner[" + id + "]";
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
