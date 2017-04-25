/*
 * Copyright (c) 2017 Nova Ordis LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.novaordis.playground.jee.ejb.mdb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author <a href="mailto:ovidiu@novardis.com">Ovidiu Feodorov</a>
 * @version <tt>$Revision: 1.2 $</tt>
 */
@SuppressWarnings("unused")
@MessageDriven(activationConfig = {

        @ActivationConfigProperty(propertyName="destinationType", propertyValue="javax.jms.Queue"),
        @ActivationConfigProperty(propertyName="destination", propertyValue="/jms/queue/playground"),
})
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class FailureHandlingExampleMDB implements MessageListener {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(FailureHandlingExampleMDB.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    @Resource
    private MessageDrivenContext context;
//    private DeliveryAttemptCounter deliveryAttemptCounter;
//    private FailureCounter failureCounter;

    // Constructors ----------------------------------------------------------------------------------------------------

    public FailureHandlingExampleMDB() {

//        deliveryAttemptCounter = DeliveryAttemptCounter.getInstance();
//        failureCounter = FailureCounter.getInstance();

        log.info(this + " constructed");
    }

    // Public ----------------------------------------------------------------------------------------------------------

    @Override
    public void onMessage(Message message) {

        try {

            //
            // the execution of the business logic takes place within the context of a JTA "message delivery"
            // transaction
            //

            //
            // process the message and interact with transactional resources
            //

            TextMessage tm = (TextMessage)message;

            String result = businessLogic(tm.getText());

            writeToDatabase(result);

        }
        catch(MyApplicationException e) {

            //
            // this is an application exception thrown by our application code; we need to decide whether
            // to rollback the transaction or not, it's an application-specific decision
            //
        }
        catch(Exception e) {

            //
            // declare that the message was not processed and it has to be "put back" on the queue
            //

            context.setRollbackOnly();
        }
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

        return "FailureHandlingExampleMDB[" + Integer.toHexString(System.identityHashCode(this)) + "]";
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    private String businessLogic(String s) {

        String result = "";

        for(int i = s.length() - 1; i >= 0; i ++) {

            result += s.charAt(i);
        }

        return result;
    }

    private void writeToDatabase(String s) {

        log.info("\"" + s + "\" written to database");
    }

    // Inner classes ---------------------------------------------------------------------------------------------------
}
