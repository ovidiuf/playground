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

package io.novaordis.playground.http.server;

import io.novaordis.playground.http.server.rhandler.RequestHandler;
import io.novaordis.utilities.NotYetImplementedException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/6/17
 */
public class MockHttpServer implements HttpServer {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private List<RequestHandler> handlers;

    // Constructors ----------------------------------------------------------------------------------------------------

    public MockHttpServer() {

        this.handlers = new ArrayList<>();
    }

    // Server implementation  ------------------------------------------------------------------------------------------

    @Override
    public File getDocumentRoot() {

        return new File(".");
    }

    @Override
    public String getServerType() {

        return "Mock";
    }

    @Override
    public void exit(long initiateShutdownDelayMs) {
        throw new NotYetImplementedException("exit() NOT YET IMPLEMENTED");
    }

    @Override
    public List<RequestHandler> getHandlers() {

        return handlers;
    }

    // Public ----------------------------------------------------------------------------------------------------------

    public void addHandler(RequestHandler handler) {

        handlers.add(handler);
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
