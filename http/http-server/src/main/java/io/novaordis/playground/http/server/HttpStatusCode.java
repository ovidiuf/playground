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

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/4/17
 */
public enum HttpStatusCode {

    OK(200, "OK"),
    BAD_REQUEST(400, "Bad Request");

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
