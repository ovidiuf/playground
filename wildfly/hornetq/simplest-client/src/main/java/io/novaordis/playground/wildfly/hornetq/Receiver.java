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

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import java.util.Timer;
import java.util.concurrent.atomic.AtomicLong;

public class Receiver
{
    // Constants -------------------------------------------------------------------------------------------------------

    public static long REPORTER_PERIOD = 2000L;

    // Static ----------------------------------------------------------------------------------------------------------

    public static void main(String[] args) throws Exception
    {
        Configuration conf = new Configuration(args, false, false);

        String jndiUrl = conf.getJndiUrl();
        String destinationName = conf.getDestinationName();
        String connectionFactoryName = conf.getConnectionFactoryName();

        Destination destination = JNDI.getDestination(jndiUrl, destinationName);
        System.out.println("destination: " + destination);

        ConnectionFactory connectionFactory = JNDI.getConnectionFactory(jndiUrl, connectionFactoryName);
        System.out.println("connection factory: " + connectionFactory);

        Connection connection = connectionFactory.createConnection(conf.getUserName(), conf.getPassword());
        System.out.println("connection: " + connection);

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageConsumer consumer = session.createConsumer(destination);

        AtomicLong messageReceived = new AtomicLong(0);
        consumer.setMessageListener(new SimpleListener(messageReceived));

        Timer timer = new Timer();
        timer.schedule(new MessageReceivedReporter(messageReceived), REPORTER_PERIOD, REPORTER_PERIOD);

        Runtime.getRuntime().addShutdownHook(new ReceiverShutdownHook(connection, messageReceived));

        connection.start();

        System.out.println("listening for messages");
    }

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
