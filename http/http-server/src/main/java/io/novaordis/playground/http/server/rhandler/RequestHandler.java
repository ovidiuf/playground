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

import io.novaordis.playground.http.server.ConnectionHandler;
import io.novaordis.playground.http.server.http.HttpRequest;
import io.novaordis.playground.http.server.http.HttpResponse;

/**
 * Handles a HttpRequest by turning it into a HttpResponse.
 *
 * Must be thread-safe, all requests of the same type will be handled concurrently by the same instance.
 *
 * @see ConnectionHandler
 *
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/6/17
 */
public interface RequestHandler {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    /**
     * @return true if this handler can handle the given request, false otherwise. In case multiple handlers can handle
     * the request, the calling layer will decide which one to use.
     */
    boolean accepts(HttpRequest request);

    /**
     * Must not throw exceptions, but log and return appropriate 5XX HttpResponses.
     *
     * If the request handler gets a request it cannot handle (to which accepts() would have answered false)
     * log and return 500 Internal Server Error.
     *
     * Must establish the response reference to request.
     *
     * @see HttpResponse#setRequest(HttpRequest)
     */
    HttpResponse processRequest(HttpRequest request);


}
