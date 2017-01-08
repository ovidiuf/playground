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

package io.novaordis.playground.http.server.http;

import io.novaordis.playground.http.server.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/4/17
 */
public enum HttpStatusCode {

    OK(200, "OK"),

    BAD_REQUEST(400, "Bad Request"),
    NOT_FOUND(404, "Not Found"),
    UNPROCESSABLE_ENTITY(422, "Unprocessable Entity"),

    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    ;

    private static final Logger log = LoggerFactory.getLogger(HttpStatusCode.class);

    /**
     * Parses a status line and extracts the corresponding status code.
     *
     * @return null if no status code is identified or an inconsistency, such as invalid HTTP version, occurs.
     */
    public static HttpStatusCode fromStatusLine(String statusLine) throws InvalidHttpResponseException {

        if (!statusLine.startsWith(HttpServer.SUPPORTED_HTTP_VERSION)) {

            return null;
        }

        statusLine = statusLine.substring(HttpServer.SUPPORTED_HTTP_VERSION.length()).trim();

        int i = statusLine.indexOf(' ');
        i = i == -1 ? statusLine.length() : i;

        String sc = statusLine.substring(0, i);

        int sci;

        try {

            sci = Integer.parseInt(sc);
        }
        catch(Exception e ) {

            return null;
        }

        for(HttpStatusCode c: values()) {

            if (c.getStatusCode() == sci) {

                return c;
            }
        }

        if (sci < 100 || sci >= 600) {

            return null;
        }

        log.debug(sci + " not found among declared StatusCodes");
        return null;
    }

    private int statusCode;
    private String reasonPhrase;

    HttpStatusCode(int statusCode, String reasonPhrase) {

        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
    }

    /**
     * @return the numeric status code
     */
    public int getStatusCode() {

        return statusCode;
    }

    /**
     * @return the reason phrase, as it is supposed to be used in the status line.
     */
    public String getReasonPhrase() {

        return reasonPhrase;
    }

}
