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
public class FailureCounter {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(FailureCounter.class);

    // Static ----------------------------------------------------------------------------------------------------------

    private static FailureCounter singleton;

    public static FailureCounter getInstance() {

        synchronized (FailureCounter.class) {

            if (singleton == null) {

                singleton = new FailureCounter();
            }

            return singleton;
        }
    }

    // Attributes ------------------------------------------------------------------------------------------------------

    private AtomicInteger failureCounter;

    // Constructors ----------------------------------------------------------------------------------------------------

    public FailureCounter() {

        failureCounter = new AtomicInteger(0);
    }

    // Public ----------------------------------------------------------------------------------------------------------

    public int incrementFailureCounter() {

        return failureCounter.incrementAndGet();
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
