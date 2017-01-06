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

import io.novaordis.playground.http.server.http.header.HttpEntityHeader;
import io.novaordis.playground.http.server.http.header.HttpHeader;
import io.novaordis.playground.http.server.http.header.HttpHeaderDefinition;

import java.util.ArrayList;
import java.util.List;

/**
 * The base implementation for HTTP Requests and Response.
 *
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/6/17
 */
class MessageImpl implements Message {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    //
    // maintains the headers in the order they were read from the socket
    //
    private List<HttpHeader> headers;

    //
    // the entity body; may be null. If not null, the body length and the value of the Content-Length header must always
    // be kept in in sync by the implementation
    //
    private byte[] body;


    // Constructors ----------------------------------------------------------------------------------------------------

    MessageImpl() {

        this.headers = new ArrayList<>();
    }

    //
    // Header management -----------------------------------------------------------------------------------------------
    //

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

    //
    // Body management -------------------------------------------------------------------------------------------------
    //

    /**
     * @return the actual storage, not a copy.
     */
    @Override
    public byte[] getBody() {

        return body;
    }

    @Override
    public void setBody(byte[] b) {

        this.body = b;

        String lengthAsString = b == null ? "0" : Integer.toString(b.length);
        overwriteHeader(new HttpHeader(HttpEntityHeader.CONTENT_LENGTH, lengthAsString));
    }


    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    /**
     * Overwrites the header, if exists. Only used internally, as this is not standard behavior, addHeader() adds
     * headers, even if with the same name.
     *
     * @exception IllegalArgumentException if the header we're attempting to overwrite has more than one copies
     * in storage.
     */
    void overwriteHeader(HttpHeader header) {

        int index = -1;

        for(int i = 0; i < headers.size(); i ++) {

            HttpHeader h = headers.get(i);

            if (h.getFieldName().equalsIgnoreCase(header.getFieldName())) {

                if (index != -1) {

                    throw new IllegalArgumentException(
                            "header \"" + header.getFieldName() + "\" has more than one copy, cannot overwrite");
                }

                index = i;
            }
        }

        if (index == -1) {

            //
            // not found, at at the end
            //

            headers.add(header);
        }
        else {

            //
            // replace
            //

            headers.set(index, header);
        }
    }

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
