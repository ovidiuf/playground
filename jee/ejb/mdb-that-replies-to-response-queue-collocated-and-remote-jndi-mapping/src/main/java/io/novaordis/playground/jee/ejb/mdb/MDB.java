package io.novaordis.playground.jee.ejb.mdb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
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

import org.jboss.ejb3.annotation.ResourceAdapter;

/**
 * An MDB that receives and sends messages and will work without any coding changes with a collocated JMS provider, as
 * well as with a remote JMS provider.
 *
 *
 * @author <a href="mailto:ovidiu@novardis.com">Ovidiu Feodorov</a>
 * @version <tt>$Revision: 1.2 $</tt>
 */
@SuppressWarnings("unused")
@MessageDriven(activationConfig = {

        @ActivationConfigProperty(propertyName="destinationType", propertyValue="javax.jms.Queue"),

        //
        // TODO
        //

        @ActivationConfigProperty(propertyName="destination", propertyValue="java:/jms/queue/inbound-queue"),

})

//
// We are using a system property to specify the name of the resource adaptor because the name is different depending
// on whether the MDB is supposed to use the collocated JMS provider ("hornetq-ra") or a remote JMS provider
// ("hornetq-remote-ra")
//
@ResourceAdapter("${resource.adapter.name}")
public class MDB implements MessageListener {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(MDB.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------


    //
    // TODO
    //

    @Resource(name="java:/jms/queue/outbound-queue")
    private Queue responseQueue;

    //
    // We are using a system property because the JNDI name of the outbound connection factory is different depending
    // on whether the JMS provider is collocated ("java:/JmsXA") or remote ("java:/RemoteJmsXA").
    //

    @Resource(name="${outbound.connection.factory.jndi.name}")
    private ConnectionFactory responseConnectionFactory;

    private Connection responseConnection;

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    @Override
    public void onMessage(Message message) {

        //
        // this example will only work with TextMessages
        //

        TextMessage tm = (TextMessage)message;

        Session s = null;

        try {

            String text = tm.getText();

            log.info(this + " received message \"" + text + "\"");

            s = responseConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            MessageProducer p = s.createProducer(responseQueue);

            Message m = s.createTextMessage("processed " + text);

            p.send(m);

            log.info(this + " sent response");

        }
        catch(Exception e) {

            log.error(e.getMessage(), e);
        }
        finally {

            if (s != null) {

                try {

                    s.close();
                }
                catch (Exception e) {

                    log.error("failed to close session", e);
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

    //
    // We initialize the response queue and the response connection factory only once, when the instance is created
    //
    @PostConstruct
    private void initializeResponseInfrastructure() throws Exception {

        log.info(this + " is initializing the response infrastructure");

        responseConnection = responseConnectionFactory.createConnection();

        log.info(this + " created response connection " + responseConnection);

    }

    //
    // We discard the response queue and the response connection factory only once, when the instance is destroyed
    //
    @PreDestroy
    private void tearDownResponseInfrastructure() {

        log.info(this + " is tearing down the response infrastructure");

        try {

            responseConnection.close();
            responseConnection = null;
        }
        catch (Exception e) {

            log.error("failed to close connection", e);
        }
    }

    // Inner classes ---------------------------------------------------------------------------------------------------
}
