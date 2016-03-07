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

package io.novaordis.playground.wildfly.hornetq;

import io.novaordis.playground.wildfly.hornetq.util.Configuration;
import io.novaordis.playground.wildfly.hornetq.util.JNDI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

public class Sender
{
    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    // Static ----------------------------------------------------------------------------------------------------------

    public static void main(String[] args) throws Exception
    {
        Configuration conf = new Configuration(args, true, true);

        String jndiUrl = conf.getJndiUrl();

        String destinationName = conf.getDestinationName();
        Destination destination = JNDI.getDestination(jndiUrl, destinationName);
        log.info("destination: " + destination);

        String connectionFactoryName = conf.getConnectionFactoryName();
        ConnectionFactory connectionFactory = JNDI.getConnectionFactory(jndiUrl, connectionFactoryName);
        log.info("connection factory: " + destination);

        Connection connections[] = new Connection[1];

        for(int i = 0; i < connections.length; i ++)
        {
            String username = conf.getUserName();

            if (username != null)
            {
                connections[i] = connectionFactory.createConnection(username, conf.getPassword());
            }
            else
            {
                connections[i] = connectionFactory.createConnection();
            }

            log.info("connection[" + i + "]: " + connections[i]);
        }

        int threadCount = conf.getThreadCount();
        int messageCount = conf.getMessageCount();

        log.info("sending " + messageCount + " messages on " + threadCount + " thread(s) ...");

        for (Connection connection : connections)
        {
            connection.start();

            log.info(connection + " started");
        }

        final AtomicInteger remainingToSend = new AtomicInteger(messageCount);
        final AtomicInteger messagesSent = new AtomicInteger(0);
        final CyclicBarrier barrier = new CyclicBarrier(threadCount + 1);

        for(int i = 0; i < threadCount; i ++)
        {
            new Thread(new SingleThreadedSender(
                i, connections[i % connections.length], destination, remainingToSend, messagesSent, barrier),
                "Sender Thread " + i).start();
        }

        barrier.await();

        for (Connection connection : connections)
        {
            connection.close();
            log.info(connection + " closed");
        }

        log.info("sender done, " + messagesSent.get() + " messages sent");
    }

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
