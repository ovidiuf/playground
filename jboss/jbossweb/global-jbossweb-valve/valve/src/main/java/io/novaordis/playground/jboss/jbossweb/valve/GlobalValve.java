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

package io.novaordis.playground.jboss.jbossweb.valve;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ValveBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 2/27/17
 */
public class GlobalValve extends ValveBase {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(GlobalValve.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private String someConfigurationParameter;

    // Constructors ----------------------------------------------------------------------------------------------------

    /**
     * Invoked by reflection.
     */
    @SuppressWarnings("unused")
    public GlobalValve() {

        log.info(this + " constructed");
    }

    // ValveBase overrides ---------------------------------------------------------------------------------------------

    @Override
    public void invoke(Request request, Response response) throws IOException, ServletException {

        getNext().invoke(request, response);
    }

    // Public ----------------------------------------------------------------------------------------------------------

    /**
     * Invoked via reflection by the WildFly initialization runtime.
     */
    @SuppressWarnings("unused")
    public void setSomeConfigurationParameter(String s) {

        this.someConfigurationParameter = s;

        log.info("someConfigurationParameter initialized with " + s);
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
