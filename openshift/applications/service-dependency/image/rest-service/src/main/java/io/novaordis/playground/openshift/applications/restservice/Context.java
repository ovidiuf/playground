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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 2/26/18
 */
public class Context {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private HttpServletRequest request;
    private HttpServletResponse response;
    private Service service;
    private Console console;

    // Constructors ----------------------------------------------------------------------------------------------------

    public Context(HttpServletRequest request, HttpServletResponse response, Service service) {

        this.request = request;
        this.response = response;
        this.service = service;
        this.console = new Console();
    }

    // Public ----------------------------------------------------------------------------------------------------------

    public void init() {

    }

    public HttpServletRequest getRequest() {

        return request;
    }

    public HttpServletResponse getResponse() {

        return response;
    }

    /**
     * An instance to use to send info/warn/error messages to the client. They will be rendered appropriately in
     * HTML, and also logged into the server log. The messages should be readable text to be displayed on the client to
     * inform the user on the outcome of a command or other events.
     */
    public Console getConsole() {

        return console;
    }

    public Service getService() {

        return service;
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
