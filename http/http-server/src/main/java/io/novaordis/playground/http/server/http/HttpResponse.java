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

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/5/17
 */
public class HttpResponse extends HeadersImpl {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    public static String showResponse(HttpResponse r) {

        byte[] b = r.statusLineAndHeadersToWireFormat();

        String s = new String(b);

        byte[] bc = r.getEntityBodyContent();

        if (bc != null) {

            s += "... (" + bc.length + " bytes)";
        }

        return s;
    }

    // Attributes ------------------------------------------------------------------------------------------------------

    private HttpStatusCode statusCode;
    private byte[] entityBodyContent;


    // Constructors ----------------------------------------------------------------------------------------------------

    public HttpResponse() {

        this(null);
    }

    public HttpResponse(HttpStatusCode statusCode) {

        this(statusCode, null);
    }

    public HttpResponse(HttpStatusCode statusCode, byte[] entityBodyContent) {

        this.statusCode = statusCode;
        this.entityBodyContent = entityBodyContent;
    }

    // Public ----------------------------------------------------------------------------------------------------------

    public HttpStatusCode getStatusCode() {

        return statusCode;
    }

    public void setStatusCode(HttpStatusCode c) {

        this.statusCode = c;
    }

    public String getHttpVersion() {

        return "HTTP/1.1";
    }

    /**
     * @return may return null if there is no content. Returns the actual storage, not a copy.
     */
    public byte[] getEntityBodyContent() {

        return entityBodyContent;
    }

    /**
     * @param b may be null.
     *
     * Automatically adds the appropriate Content-Length header.
     */
    public void setEntityBodyContent(byte[] b) {

        this.entityBodyContent = b;

        String lengthAsString = b == null ? "0" : Integer.toString(b.length);
        addHeader(HttpEntityHeader.CONTENT_LENGTH, lengthAsString);
    }

    /**
     * @return the wire format representation of the status line and headers. It also contains the blank line. This is
     * the content to be sent to the client, but without the body.
     */
    public byte[] statusLineAndHeadersToWireFormat() {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {

            baos.write(getHttpVersion().getBytes());
            baos.write(' ');
            baos.write(Integer.toString(statusCode.getStatusCode()).getBytes());
            baos.write(' ');
            baos.write(statusCode.getReasonPhrase().getBytes());
            baos.write('\r');
            baos.write('\n');

            for(HttpHeader h: getHeaders()) {

                baos.write(h.toWireFormat());
                baos.write('\r');
                baos.write('\n');
            }

            baos.write('\r');
            baos.write('\n');
        }
        catch(IOException e) {

            //
            // must not ever happen with a ByteArrayOutputStream
            //
            throw new IllegalArgumentException("internal error", e);
        }

        return baos.toByteArray();
    }

    @Override
    public String toString() {

        return getHttpVersion() + " " + statusCode.getStatusCode() + " " + statusCode.getReasonPhrase();
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
