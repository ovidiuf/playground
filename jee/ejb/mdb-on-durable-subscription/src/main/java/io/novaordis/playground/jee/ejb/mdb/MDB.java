package io.novaordis.playground.jee.ejb.mdb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.Message;
import javax.ejb.MessageDriven;
import javax.ejb.ActivationConfigProperty;
import javax.jms.TextMessage;
import java.util.Map;

/**
 * @author <a href="mailto:ovidiu@novardis.com">Ovidiu Feodorov</a>
 * @version <tt>$Revision: 1.2 $</tt>
 */
@SuppressWarnings("unused")
@MessageDriven(activationConfig = {

        @ActivationConfigProperty(propertyName="destinationType", propertyValue="javax.jms.Topic"),
        @ActivationConfigProperty(propertyName="destination", propertyValue="/jms/topic/playground-topic"),
        @ActivationConfigProperty(propertyName="subscriptionDurability", propertyValue="Durable"),
        @ActivationConfigProperty(propertyName="subscriptionName", propertyValue="something"),
})
public class MDB implements MessageListener {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(MDB.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    @Resource
    private MessageDrivenContext context;

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

        log.info("message driven context: " + context);

        log.info("is current transaction marked for rollback? " + context.getRollbackOnly());

        Map<String, Object> contextData = context.getContextData();

        log.info("context data: " + contextData.size());
    }

    //
    // Container callbacks ---------------------------------------------------------------------------------------------
    //

    @PostConstruct
    public void weHaveJustBeenCreated() {

        log.info(this + " was notified by the container that we've just been created");
    }


    @PreDestroy
    public void weAreAboutToBeDestroyed() {

        log.info(this + " was notified by the container that it is about to be destroyed");
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
