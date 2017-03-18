package io.novaordis.playground.jee.ejb.mdb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.Message;
import javax.ejb.MessageDriven;
import javax.ejb.ActivationConfigProperty;
import javax.jms.TextMessage;
import org.jboss.ejb3.annotation.ResourceAdapter;

/**
 * @author <a href="mailto:ovidiu@novardis.com">Ovidiu Feodorov</a>
 * @version <tt>$Revision: 1.2 $</tt>
 */
@SuppressWarnings("unused")
@MessageDriven(activationConfig = {

        @ActivationConfigProperty(propertyName="destinationType", propertyValue="javax.jms.Queue"),
        @ActivationConfigProperty(propertyName="destination", propertyValue="/jms/queue/remote-playground"),

})
@ResourceAdapter("hornetq-remote-ra")
public class MDB implements MessageListener {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(MDB.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

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

        try {

            String text = tm.getText();
            log.info(this + " received message \"" + text + "\"");

        }
        catch(JMSException e) {

            log.error(e.getMessage());
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
