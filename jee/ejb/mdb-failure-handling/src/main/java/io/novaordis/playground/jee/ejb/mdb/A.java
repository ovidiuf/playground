package io.novaordis.playground.jee.ejb.mdb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.MessageDrivenContext;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.Message;
import javax.ejb.MessageDriven;
import javax.ejb.ActivationConfigProperty;
import javax.jms.TextMessage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author <a href="mailto:ovidiu@novardis.com">Ovidiu Feodorov</a>
 * @version <tt>$Revision: 1.2 $</tt>
 */
//@SuppressWarnings("unused")
//@MessageDriven(activationConfig = {
//
//        @ActivationConfigProperty(propertyName="destinationType", propertyValue="javax.jms.Queue"),
//        @ActivationConfigProperty(propertyName="destination", propertyValue="/jms/queue/playground"),
////        @ActivationConfigProperty(propertyName="maxSession", propertyValue="15"),
//
//})
//@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class A /* implements MessageListener */ {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(A.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    @Resource
    private MessageDrivenContext context;

    private DeliveryAttemptCounter deliveryAttemptCounter;
    private FailureCounter failureCounter;

    // Constructors ----------------------------------------------------------------------------------------------------

    public A() {

        deliveryAttemptCounter = DeliveryAttemptCounter.getInstance();
        failureCounter = FailureCounter.getInstance();

        log.info(this + " constructed");
    }

    // Public ----------------------------------------------------------------------------------------------------------

    // @Override
    public void onMessage(Message message) {

        TextMessage tm = (TextMessage)message;

        try {

            //
            // process the message and interact with transactional resources
            //

        }
        catch(MyApplicationException e) {

            //
            // this is an application exception thrown by our application code; we need to decide whether
            // to
            //

        }

//        int deliveryAttempts = getDeliveryAttempts(message);
//        log.info("delivery attempt " + deliveryAttempts);
//
//        if (deliveryAttempts == 1) {
//
            throw new MyApplicationException(
                    "SYNTHETIC EXCEPTION #" + failureCounter.incrementFailureCounter() + " thrown by A ");

        //
        // consume
        //

//        try {
//
//            String text = tm.getText();
//            log.info(this + " received message \"" + text + "\"");
//
//        }
//        catch(JMSException e) {
//
//            log.error(e.getMessage());
//        }
//
//        log.info("message driven context: " + context);
//
//        log.info("is current transaction marked for rollback? " + context.getRollbackOnly());
//
//        Map<String, Object> contextData = context.getContextData();
//
//        log.info("context data: " + contextData.size());
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

        return "MDB A[" + Integer.toHexString(System.identityHashCode(this)) + "]";
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------
}
