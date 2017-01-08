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

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/5/17
 */
public class HttpResponse extends MessageImpl {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    /**
     * Renders the HttpResponse content as it will be sent over the wire, less the actual body. If there is a body
     * it will show as ... (... bytes).
     */
    public static String showResponse(HttpResponse r) {

        byte[] b = r.statusLineAndHeadersToWireFormat();

        String s = new String(b);

        byte[] bc = r.getBody();

        if (bc != null) {

            s += "... (" + bc.length + " bytes)";
        }

        return s;
    }

    // Attributes ------------------------------------------------------------------------------------------------------

    private HttpStatusCode statusCode;
    private HttpRequest request;

    // Constructors ----------------------------------------------------------------------------------------------------

    /**
     * Use this only when planning to further update the state.
     */
    public HttpResponse() {

        this((HttpStatusCode)null);
    }

    public HttpResponse(HttpStatusCode statusCode) {

        this(statusCode, null);
    }

    public HttpResponse(HttpStatusCode statusCode, byte[] entityBodyContent) {

        this.statusCode = statusCode;
        setBody(entityBodyContent);
    }

    /**
     * Reconstructs a HttpResponse from the wire content.
     */
    public HttpResponse(byte[] wireContent) throws InvalidHttpMessageException {

        BufferedReader r = new BufferedReader(new StringReader(new String(wireContent)));

        try {

            String line;
            boolean blankLineSeen = false;
            ByteArrayOutputStream baos = null;

            while ((line = r.readLine()) != null) {

                if (statusCode == null) {

                    //
                    // first line, status line
                    //

                    this.statusCode = HttpStatusCode.fromStatusLine(line);
                }
                else if (!blankLineSeen) {

                    if (line.trim().isEmpty()) {

                        blankLineSeen = true;
                    }
                    else {

                        //
                        // a header line
                        //
                        HttpHeader h = HttpHeader.parseHeader(line.getBytes(), 0, line.length());
                        addHeader(h);
                    }
                }
                else {

                    //
                    // body line
                    //

                    if (baos == null) {

                        baos = new ByteArrayOutputStream();
                    }

                    baos.write(line.getBytes());
                }
            }

            if (baos != null) {

                byte[] b = baos.toByteArray();

                //
                // check if the body length matches Content-Length
                //

                List<HttpHeader> hs = getHeader(HttpEntityHeader.CONTENT_LENGTH);
                if (!hs.isEmpty()) {
                    HttpHeader h = hs.get(0);
                    if (b.length != Integer.parseInt(h.getFieldBody())) {
                        throw new InvalidHttpMessageException("Content-Length/body length mismatch");
                    }
                }

                //
                // TODO what to do if Content-Length is not present
                //

                setBody(b);
            }
        }
        catch(IOException e) {

            //
            // should not happen with an in-memory reader
            //
            throw new IllegalStateException("unexpected failure on an in-memory reader", e);
        }
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

    public void setRequest(HttpRequest r) {

        this.request = r;
    }

    /**
     * @return the associated request.
     */
    public HttpRequest getRequest() {

        return request;
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

        if (statusCode == null) {

            return "uninitialized";
        }

        return getHttpVersion() + " " + statusCode.getStatusCode() + " " + statusCode.getReasonPhrase();
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
