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

import io.novaordis.playground.http.server.Server;
import io.novaordis.playground.http.server.http.HttpRequest;
import io.novaordis.playground.http.server.http.HttpResponse;
import io.novaordis.playground.http.server.http.HttpStatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/7/17
 */
public class ServerExitRequestHandler implements RequestHandler {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(ServerExitRequestHandler.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private Server server;

    // Constructors ----------------------------------------------------------------------------------------------------

    public ServerExitRequestHandler(Server server) {

        this.server = server;
    }

    // RequestHandler implementation -----------------------------------------------------------------------------------

    @Override
    public boolean accepts(HttpRequest request) {

        String path = request.getPath();
        return Server.EXIT_URL.equals(path);
    }

    @Override
    public HttpResponse processRequest(HttpRequest request) {

        String path = request.getPath();
        if (!Server.EXIT_URL.equals(path)) {

            log.error(this + " cannot handle " + request);
            return new HttpResponse(HttpStatusCode.INTERNAL_SERVER_ERROR);
        }

        log.info("\"" + Server.EXIT_URL + "\" special URL identified, initiating server shutdown ...");

        // this will shutdown asynchronously, allowing us to send the response
        server.exit(500L);

        return new HttpResponse(HttpStatusCode.OK);
    }

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
