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

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/5/17
 */
public class HttpResponse {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private HttpStatusCode statusCode;
    private byte[] entityBodyContent;

    // Constructors ----------------------------------------------------------------------------------------------------

    public HttpResponse() {

        this(null);
    }

    public HttpResponse(HttpStatusCode statusCode) {

        this.statusCode = statusCode;
    }

    // Public ----------------------------------------------------------------------------------------------------------

    public HttpStatusCode getStatusCode() {

        return statusCode;
    }

    public void setStatusCode(HttpStatusCode c) {

        this.statusCode = c;
    }

    /**
     * @return may return null if there is no content. Returns the actual storage, not a copy.
     */
    public byte[] getEntityBodyContent() {

        return entityBodyContent;
    }

    public void setEntityBodyContent(byte[] b) {

        this.entityBodyContent = b;
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
