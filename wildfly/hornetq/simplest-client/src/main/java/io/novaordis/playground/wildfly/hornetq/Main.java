package io.novaordis.playground.wildfly.hornetq;

import io.novaordis.playground.wildfly.hornetq.util.JNDI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;

public class Main
{
    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    // Static ----------------------------------------------------------------------------------------------------------

    public static void main(String[] args) throws Exception
    {
        long receiveTimeoutMs = 10000L;

        String providerUrl = "172.26.71.32:1099";

        //String destinationName = "/queue/testDistributedQueue";
        String destinationName = "/queue/toyota";

        //String connectionFactoryName = "ClusteredConnectionFactory";
        String connectionFactoryName = "ConnectionFactory";

        String username = "foo";
        String password = "bar";

        Destination d = JNDI.getDestination(providerUrl, destinationName);

        System.out.println("destination: " + d);

        ConnectionFactory cf = JNDI.getConnectionFactory(providerUrl, connectionFactoryName);

        System.out.println("connection factory: " + cf);

        Connection c = cf.createConnection(username, password);

        System.out.println("connection: " + c);

        Session s = c.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageConsumer consumer = s.createConsumer(d);

        c.start();

        int received = 0;

        while(true)
        {
            long t0 = System.currentTimeMillis();
            Message m = consumer.receive(receiveTimeoutMs);
            long t1 = System.currentTimeMillis();

            if (m == null)
            {
                // we ran out of messages
                break;
            }

            received ++;

            Statistics.record(t0, t1, received, m.getJMSMessageID());
        }

        c.stop();
        c.close();
    }


    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
