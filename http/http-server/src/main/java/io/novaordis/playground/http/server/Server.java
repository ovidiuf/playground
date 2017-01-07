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

import java.io.File;
import java.util.List;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/6/17
 */
public interface Server {

    // Constants -------------------------------------------------------------------------------------------------------

    // the path to be send into the server to shut it down
    static String EXIT_URL = "/exit";

    // Static ----------------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    File getDocumentRoot();

    /**
     * The content of the Server response header, to be returned to client.
     */
    String getServerType();

    /**
     * This method should be used by the request processing threads to message the server instance that they got the
     * exit request.
     */
    void exit(long initiateShutdownDelayMs);

    /**
     * @return the list of request handlers, in the descending order of their priority. The handlers at the top of the
     * list will have priority in declaring that they want to handle the request. The request handlers must be thread
     * safe, there is only one instance of a specific request handler per server.
     */
    List<RequestHandler> getHandlers();

}
