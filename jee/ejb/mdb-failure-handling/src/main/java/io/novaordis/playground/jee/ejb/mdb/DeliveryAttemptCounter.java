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

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 4/25/17
 */
public class DeliveryAttemptCounter {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(DeliveryAttemptCounter.class);

    // Static ----------------------------------------------------------------------------------------------------------

    private static DeliveryAttemptCounter singleton;

    public static DeliveryAttemptCounter getInstance() {

        synchronized (DeliveryAttemptCounter.class) {

            if (singleton == null) {

                singleton = new DeliveryAttemptCounter();
            }

            return singleton;
        }
    }

    // Attributes ------------------------------------------------------------------------------------------------------

    //
    // <message-id - delivery attempts>
    //
    private ConcurrentMap<String, AtomicInteger> deliveryAttemptsPerMessage;

    // Constructors ----------------------------------------------------------------------------------------------------

    public DeliveryAttemptCounter() {

        deliveryAttemptsPerMessage = new ConcurrentHashMap<>();
    }

    // Public ----------------------------------------------------------------------------------------------------------

    public int getDeliveryAttempts(Message m) {

        try {

            String s = m.getJMSMessageID();

            AtomicInteger da = deliveryAttemptsPerMessage.get(s);

            if (da == null) {

                da = new AtomicInteger(1);
                deliveryAttemptsPerMessage.put(s, da);
                return 1;
            }

            return da.incrementAndGet();
        }
        catch(JMSException e) {

            log.error("failed to get message ID", e);
            return -1;
        }
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
