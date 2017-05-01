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
import io.novaordis.playground.jee.ejb.ejb2rest.common.Measurements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

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

    //@EJB(lookup = "ejb:/callee/CalleeImpl!io.novaordis.playground.jee.ejb.ejb2rest.common.Callee")
    @Inject
    private Callee callee;

    // Constructors ----------------------------------------------------------------------------------------------------

    // LocalAccessToCaller ---------------------------------------------------------------------------------------------

    @Override
    public void triggerRemoteCall() {

        log.info("triggering remote call over " + (isEJB() ? "NATIVE REMOTING" : "REST"));

        sendOneInvocation();

        // Measurements.invokeSeriallyInALoop(callee, 100000);

        //Measurements.invokeConcurrentlyFromMultipleThreadsInALoop(callee, 10, 1000);
    }

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    private void sendOneInvocation() {

        String result = callee.businessMethodA("test");
        log.info("result: " + result);
    }

    private boolean isEJB() {

        return callee.toString().contains("Proxy for remote EJB");
    }

    private boolean isRest() {

        return !isEJB();
    }

    // Inner classes ---------------------------------------------------------------------------------------------------

}
