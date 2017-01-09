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
import io.novaordis.playground.http.server.http.InvalidHttpRequestException;
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

    private Long serverWideDelayMs;

    // Constructors ----------------------------------------------------------------------------------------------------

    /**
     * By default no delay.
     */
    public OKRequestHandler() {

        this(null);
    }

    /**
     * @param serverWideDelayMs null is acceptable, means no delay.
     */
    public OKRequestHandler(Long serverWideDelayMs) {

        this.serverWideDelayMs = serverWideDelayMs;
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

        try {
            delayIfNecessary(request.getQueryParameter("delay"));
        }
        catch(InvalidHttpRequestException e) {

            return new HttpResponse(HttpStatusCode.BAD_REQUEST, e.getMessage().getBytes());
        }

        return response;
    }

    // Public ----------------------------------------------------------------------------------------------------------

    public void setDelay(long serverWideDelayMs) {

        this.serverWideDelayMs = serverWideDelayMs;
    }

    /**
     * @return the server-wide delay in ms. null if there is no delay.
     */
    public Long getDelay() {

        return serverWideDelayMs;
    }

    @Override
    public String toString() {

        Long d = getDelay();
        String ds = d == null ? "0" : Long.toString(d);
        return "OKRequestHandler[delay=" + ds + "ms]";
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    /**
     *  Delay response for a certain number of milliseconds if the server or the request were configured to do so.
     *  The request delay value, if present, takes precedence over the server-wide value.
     *
     *  @param requestDelay the value carried by the request "delay=" query parameter. May be null.
     *
     *  @exception InvalidHttpRequestException if the delay query parameter carries an illegal value
     */
    private void delayIfNecessary(String requestDelay) throws InvalidHttpRequestException {

        Long effectiveDelay = null;

        if (requestDelay != null) {

            try {

                effectiveDelay = Long.parseLong(requestDelay);

            } catch (Exception e) {

                throw new InvalidHttpRequestException("invalid delay parameter value \"" + requestDelay + "\"");
            }
        }

        if (effectiveDelay == null) {

            //
            // fall back to server-wide value
            //
            effectiveDelay = serverWideDelayMs;
        }

        if (effectiveDelay != null) {

            log.info(this + " delaying response for " + effectiveDelay + " ms ...");

            try {

                Thread.sleep(effectiveDelay);
            }
            catch(InterruptedException e) {

                //
                // illegal state, we're not supposed to be interrupted
                //

                throw new IllegalStateException("the thread was unexpectedly interrupted", e);
            }
        }
    }

    // Inner classes ---------------------------------------------------------------------------------------------------

}
