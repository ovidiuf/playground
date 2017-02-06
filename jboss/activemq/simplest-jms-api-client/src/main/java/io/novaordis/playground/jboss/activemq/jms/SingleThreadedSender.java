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

package io.novaordis.playground.jboss.activemq.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

public class SingleThreadedSender implements Runnable
{
    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(SingleThreadedSender.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private int id;
    private AtomicInteger remainingToSend;
    private AtomicInteger messagesSent;
    private CyclicBarrier barrier;
    private Connection connection;
    private Destination destination;
    // negative or zero means "don't sleep"
    private long sleepBetweenSendsMs;

    // Constructors ----------------------------------------------------------------------------------------------------

    public SingleThreadedSender(int id, Connection connection, Destination destination,
                                long sleepBetweenSendsMs,
                                AtomicInteger remainingToSend, AtomicInteger messagesSent,
                                CyclicBarrier barrier)
    {
        this.id = id;
        this.connection = connection;
        this.destination = destination;
        this.remainingToSend = remainingToSend;
        this.messagesSent = messagesSent;
        this.barrier = barrier;
        this.sleepBetweenSendsMs = sleepBetweenSendsMs;
    }

    // Runnable implementation -----------------------------------------------------------------------------------------

    public void run()
    {
        Session session;

        log.info(this + " using connection " + connection);

        try
        {
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(destination);

            while (true)
            {
                int remaining = remainingToSend.getAndDecrement();

                if (remaining <= 0)
                {
                    break;
                }

                //Message m = session.createTextMessage("test");
                Message m = session.createTextMessage("test-" + System.currentTimeMillis());
                producer.send(m);

                remaining --;
                if (remaining < 0) {
                    remaining = 0;
                }

                if (sleepBetweenSendsMs > 0) {

                    log.info("sleeping " + (sleepBetweenSendsMs / 1000) + " second(s) after sending the message, " + remaining + " message(s) remaining ...");
                    Thread.sleep(sleepBetweenSendsMs);
                }

                messagesSent.incrementAndGet();
            }
        }
        catch(Exception e)
        {
            log.info("thread " + Thread.currentThread().getName() + " failed: " + e);
        }
        finally
        {
            try
            {
                barrier.await();
            }
            catch(Exception e)
            {
                log.info("failed to wait on barrier: " + e);
            }
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
