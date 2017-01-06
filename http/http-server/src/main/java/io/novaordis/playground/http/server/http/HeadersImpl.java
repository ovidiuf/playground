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

import io.novaordis.playground.http.server.http.header.HttpHeader;
import io.novaordis.playground.http.server.http.header.HttpHeaderDefinition;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/6/17
 */
class HeadersImpl implements Headers {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    //
    // maintains the headers in the order they were read from the socket
    //
    private List<HttpHeader> headers;

    // Constructors ----------------------------------------------------------------------------------------------------

    HeadersImpl() {

        this.headers = new ArrayList<>();
    }

    // Headers implementation ------------------------------------------------------------------------------------------

    @Override
    public void addHeader(HttpHeaderDefinition d, String fieldBody) {

        addHeader(new HttpHeader(d, fieldBody));
    }

    @Override
    public void addHeader(String fieldName, String fieldBody) {

        addHeader(new HttpHeader(fieldName, fieldBody));
    }

    @Override
    public void addHeader(HttpHeader header) {

        if (header == null) {
            throw new IllegalArgumentException("null header");
        }
        headers.add(header);
    }

    /**
     * Returns the internal storage.
     */
    @Override
    public List<HttpHeader> getHeaders() {

        return headers;
    }

    @Override
    public List<HttpHeader> getHeader(String fieldName) {

        List<HttpHeader> result = new ArrayList<>();

        //noinspection Convert2streamapi
        for(HttpHeader h: headers) {

            if (h.getFieldName().equalsIgnoreCase(fieldName)) {

                result.add(h);
            }
        }

        return result;
    }

    @Override
    public List<HttpHeader> getHeader(HttpHeaderDefinition headerDefinition) {

        return getHeader(headerDefinition.getCanonicalFieldName());
    }

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
