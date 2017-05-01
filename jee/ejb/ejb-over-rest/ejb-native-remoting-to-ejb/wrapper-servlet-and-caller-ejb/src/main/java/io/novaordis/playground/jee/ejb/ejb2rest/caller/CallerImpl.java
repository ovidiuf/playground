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

package io.novaordis.playground.jee.ejb.ejb2rest.caller;

import io.novaordis.playground.jee.ejb.ejb2rest.common.Callee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 5/1/17
 */
@SuppressWarnings("unused")
@Stateless
public class CallerImpl implements LocalAccessToCaller {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(CallerImpl.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    @EJB(lookup = "ejb:/callee-ejb/CalleeImpl!io.novaordis.playground.jee.ejb.ejb2rest.common.Callee")
    private Callee callee;

    // Constructors ----------------------------------------------------------------------------------------------------

    // LocalAccessToCaller ---------------------------------------------------------------------------------------------

    @Override
    public void triggerRemoteCall() {

        log.info("triggering remote call");

//        sendOneInvocation();
        invokeSeriallyInALoop(10000);
//        invokeConcurrentlyFromMultipleThreadsInALoop();

    }

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    private void sendOneInvocation() {

        String result = callee.businessMethodA("test");
        log.info("result: " + result);
    }

    private void invokeSeriallyInALoop(int invocationCount) {

        long[] callDuration = new long[invocationCount];

        log.info("invoking serially in a loop, " + invocationCount + " invocations ...");

        for(int i = 0; i < invocationCount; i ++) {

            long t0 = System.currentTimeMillis();

            String result = callee.businessMethodA("test");

            long t1 = System.currentTimeMillis();

            callDuration[i] = t1 - t0;
        }

        String s = "";
        double total = 0d;

        for (int i =  0; i < invocationCount; i ++) {

            total += callDuration[i];
            s += callDuration[i];

            if (i < invocationCount - 1) {

                s += ", ";
            }
        }

        log.info(s);

        log.info("average call duration " + (total / invocationCount));
    }

    private void invokeConcurrentlyFromMultipleThreadsInALoop() {

        throw new RuntimeException("NYE");
    }

    // Inner classes ---------------------------------------------------------------------------------------------------

}
