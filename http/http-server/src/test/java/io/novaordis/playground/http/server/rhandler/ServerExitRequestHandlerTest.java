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

package io.novaordis.playground.http.server.rhandler;

import io.novaordis.playground.http.server.MockServer;
import io.novaordis.playground.http.server.Server;
import io.novaordis.playground.http.server.http.HttpMethod;
import io.novaordis.playground.http.server.http.HttpRequest;
import io.novaordis.playground.http.server.http.HttpResponse;
import io.novaordis.playground.http.server.http.HttpStatusCode;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/5/17
 */
public class ServerExitRequestHandlerTest extends RequestHandlerTest {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    // Tests -----------------------------------------------------------------------------------------------------------

    @Test
    public void exitUrl() throws Exception {

        ServerExitRequestHandler h = getRequestHandlerToTest();

        HttpRequest r = new HttpRequest(HttpMethod.GET, Server.EXIT_URL);

        assertTrue(h.accepts(r));
    }

    @Test
    public void inappropriateUrl() throws Exception {

        ServerExitRequestHandler h = getRequestHandlerToTest();

        HttpRequest r = new HttpRequest(HttpMethod.GET, "/something");

        HttpResponse resp = h.processRequest(r);

        assertEquals(HttpStatusCode.INTERNAL_SERVER_ERROR, resp.getStatusCode());
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    @Override
    protected ServerExitRequestHandler getRequestHandlerToTest() {

        return new ServerExitRequestHandler(new MockServer());
    }

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
