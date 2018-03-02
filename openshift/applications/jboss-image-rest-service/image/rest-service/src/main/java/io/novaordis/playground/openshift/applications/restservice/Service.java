/*
 * Copyright (c) 2018 Nova Ordis LLC
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

package io.novaordis.playground.openshift.applications.restservice;

/**
 * The service can be started by default if REST_SERVICE_START environment variable is set to "true"
 *
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 2/28/18
 */
public class Service {

    // Constants -------------------------------------------------------------------------------------------------------

    public static final String REST_SERVICE_START_ENV_VAR_NAME = "REST_SERVICE_START";

    private static final Logger log = new StdoutLogger(RestServlet.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private volatile boolean started;

    // Constructors ----------------------------------------------------------------------------------------------------

    public Service() {

        String s = System.getenv(REST_SERVICE_START_ENV_VAR_NAME);

        if (s != null && s.equalsIgnoreCase("true")) {

            started = true;
        }

        if (started) {

            log.info(this + " started by default");

        }
        else {

            log.info(this + " not started");
        }

    }

    // Public ----------------------------------------------------------------------------------------------------------

    public boolean isStarted() {

        return started;
    }

    public void start() {

        this.started = true;
    }

    @Override
    public String toString() {

        return "Service[" + Integer.toHexString(System.identityHashCode(this)) + "]";
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
