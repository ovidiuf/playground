package io.novaordis.playground.jee.ejb.mdb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageListener;
import javax.jms.Message;
import javax.ejb.MessageDriven;
import javax.ejb.ActivationConfigProperty;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

import org.jboss.ejb3.annotation.ResourceAdapter;

/**
 * @author <a href="mailto:ovidiu@novardis.com">Ovidiu Feodorov</a>
 * @version <tt>$Revision: 1.2 $</tt>
 */
@SuppressWarnings("unused")
@MessageDriven(activationConfig = {

        @ActivationConfigProperty(propertyName="destinationType", propertyValue="javax.jms.Queue"),
        @ActivationConfigProperty(propertyName="destination", propertyValue="/jms/queue/remote-inbound"),

})
@ResourceAdapter("hornetq-remote-ra")
public class MDB implements MessageListener {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(MDB.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    @Resource(lookup = "java:global/remote-hornetq")
    private InitialContext externalContext;

    @Resource(name = "java:/RemoteJmsXA")
    private ConnectionFactory cf;

    // Constructors ----------------------------------------------------------------------------------------------------

    public MDB() {

        log.info(this + " constructed");
    }

    // Public ----------------------------------------------------------------------------------------------------------

    @Override
    public void onMessage(Message message) {

        //
        // this example will only work with TextMessages
        //

        TextMessage tm = (TextMessage)message;

        Connection c = null;

        try {

            String text = tm.getText();

            log.info(this + " received message \"" + text + "\"");

            //
            // look up the response destination and send a response message
            //

            Queue responseQueue = (Queue)externalContext.lookup("/jms/queue/remote-outbound");

            c = cf.createConnection();

            Session s = c.createSession(false, Session.AUTO_ACKNOWLEDGE);

            MessageProducer p = s.createProducer(responseQueue);

            Message m = s.createTextMessage("processed " + text);

            p.send(m);

            log.info(this + " sent response");

        }
        catch(Exception e) {

            log.error(e.getMessage());
        }
        finally {

            if (c != null) {

                try {
                    c.close();
                }
                catch (Exception e) {

                    log.error("failed to close connection", e);
                }
            }
        }
    }

    @Override
    public String toString() {

        return "MDB[" + Integer.toHexString(System.identityHashCode(this)) + "]";
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------
}
