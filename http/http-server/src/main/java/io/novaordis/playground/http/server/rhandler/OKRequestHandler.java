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

import io.novaordis.playground.http.server.http.HttpRequest;
import io.novaordis.playground.http.server.http.HttpResponse;
import io.novaordis.playground.http.server.http.HttpStatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A handler that responds with a 200 OK and an "ok" body to any request it sees.
 *
 * Optionally, it can be configured to delay the answer for a certain number of milliseconds.
 *
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/5/17
 */
public class OKRequestHandler implements RequestHandler {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(OKRequestHandler.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private Long delayMs;

    // Constructors ----------------------------------------------------------------------------------------------------

    public OKRequestHandler() {

        // by default no delay
        this.delayMs = null;
    }

    // RequestHandler implementation -----------------------------------------------------------------------------------

    @Override
    public boolean accepts(HttpRequest request) {

        return true;
    }

    @Override
    public HttpResponse processRequest(HttpRequest request) {

        HttpResponse response = new HttpResponse(HttpStatusCode.OK);
        response.setRequest(request);
        response.setBody("OK\n".getBytes()); // this will also set Content-Length

        //
        // delay response for a certain number of milliseconds if configured so
        //

        if (delayMs != null) {

            log.info(this + " delaying response for " + delayMs + " ms ...");

            try {

                Thread.sleep(delayMs);
            }
            catch(InterruptedException e) {

                //
                // illegal state, we're not supposed to be interrupted
                //

                throw new IllegalStateException("the thread was unexpectedly interrupted", e);
            }
        }

        return response;
    }

    // Public ----------------------------------------------------------------------------------------------------------

    public void setDelay(long delayMs) {

        this.delayMs = delayMs;
    }

    /**
     * @return the delay in ms. null if there is no delay.
     */
    public Long getDelay() {

        return delayMs;
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
