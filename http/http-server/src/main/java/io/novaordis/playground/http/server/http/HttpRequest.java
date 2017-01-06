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

import io.novaordis.playground.http.server.connection.Connection;
import io.novaordis.playground.http.server.connection.ConnectionException;
import io.novaordis.playground.http.server.http.header.HttpHeader;
import io.novaordis.playground.http.server.http.header.InvalidHttpHeaderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/4/17
 */
public class HttpRequest extends HeadersImpl {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    public static final String DEFAULT_HTTP_VERSION = "HTTP/1.1";

    // Static ----------------------------------------------------------------------------------------------------------

    /**
     * Read a request from the current position in the given connection.
     *
     * @return a valid request, or returns null if we find the socket's input stream closed. This does not necessarily
     * mean the connection is closed, but once we detect this condition, the upper layer should close it and discard
     * it.
     *
     * @exception ConnectionException  if a connection-related failure occurs. ConnectionException are only used to
     * indicate Connection bad quality or instability. Once a ConnectionException has been triggered by a Connection
     * instance, the connection instance should be generally considered unreliable. The calling layer should close it as
     * soon as possible.
     *
     * @exception InvalidHttpRequestException if we fail to parse the data arrived on the wire into a HttpRequest.
     */
    public static HttpRequest readRequest(Connection connection)
            throws ConnectionException, InvalidHttpRequestException {

        RequestBuffer buffer = new RequestBuffer();

        //
        // read up to the blank line - this will include the request line and all headers
        //

        boolean newLine = false;
        boolean lfExpected = false;

        while(true) {

            int i = connection.read();

            if (i == -1) {

                //
                // we found the input stream closed on the first read, or after some irrelevant content has
                // accumulated in the buffer, return null to tell the upper layer that this connection has become
                // useless; it will probably close it
                //

                if (buffer.allDiscardableContent()) {

                    return null;
                }

                //
                // input stream closed while reading a request from the socket, this is an interesting situation as it
                // should not happen, but process it nonetheless, build the request and let the response logic deal with
                // it
                //

                log.warn("connection closed while reading a request");

                break;
            }

            if (lfExpected) {

                if (i != '\n') {

                    throw new InvalidHttpHeaderException("missing LF");
                }

                lfExpected = false;
            }


            if ('\r' == (char)i) {

                if (newLine) {

                    //
                    // blank line encountered, but loop one more time to consume the LF on the wire
                    //

                    lfExpected = true;
                }

                //
                // ignore it
                //

                continue;

            }
            else if ('\n' == (char)i) {

                if (newLine) {

                    //
                    // blank line encountered, or we're the first and the last line; it will NOT be written into the
                    // header buffer
                    //

                    break;
                }

                newLine = true;

                //
                // write it into the header buffer
                //
            }
            else {

                newLine = false;
            }

            buffer.write(i);
        }

        return new HttpRequest(buffer.toByteArray());
    }

    public static String showRequest(HttpRequest r) {

        String s = "";

        s += r.getMethod() + " " + r.getPath() + " " + r.getHttpVersion() + "\n";

        for(HttpHeader h: r.getHeaders()) {

            s += new String(h.toWireFormat()) + "\n";
        }

        return s;
    }

    // Static package protected ----------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private byte[] content;
    private HttpMethod method;
    private String path;
    private String version;

    // Constructors ----------------------------------------------------------------------------------------------------

    /**
     * Initializes common data structures.
     */
    private HttpRequest() {

        super();
    }

    public HttpRequest(HttpMethod method, String path) {

        this();
        this.method = method;
        this.path = path;
        this.version = DEFAULT_HTTP_VERSION;
        this.content = (method + " " + path + " " + version).getBytes();
    }

    /**
     * The header content, as read from the socket. Does not include the blank line.
     *
     * @exception InvalidHttpHeaderException on faulty content that cannot be translated into a valid HTTP header
     */
    HttpRequest(byte[] content) throws InvalidHttpRequestException {

        this();
        this.content = content;

        int from = 0;

        for(int i = 0; i < content.length; i ++) {

            if (content[i] == '\n' || i == content.length - 1) {

                //
                // correction for the last element
                //
                i = i == content.length - 1 && content[i] != '\n' ? i + 1 : i;

                if (from == 0) {

                    parseFirstLine(content, from, i);
                }
                else {

                    HttpHeader h = HttpHeader.parseHeader(content, from, i);
                    addHeader(h);
                }

                from = i + 1;
            }
        }
    }

    // Public ----------------------------------------------------------------------------------------------------------

    public HttpMethod getMethod() {

        return method;
    }

    public String getPath() {

        return path;
    }

    /**
     * @return the HTTP version string as extracted from the first line of the request.
     */
    public String getHttpVersion() {

        return version;
    }

    @Override
    public String toString() {

        if (content == null) {
            return "null content";
        }

        return getMethod() + " " + getPath() + " " + getHttpVersion();
    }

    // Package protected -----------------------------------------------------------------------------------------------

    /**
     * @param to the index of the first element <b>to ignore</b>. It may extend beyond the array boundary and the
     *           method implementation should be prepared to deal with this.
     */
    void parseFirstLine(byte[] content, int from, int to) throws InvalidHttpRequestException {

        int pathIndex = -1;
        int versionIndex = -1;
        int rightLimit = to >= content.length ? content.length : to;

        for(int i = from; i < rightLimit; i ++) {

            if (content[i] == ' ') {

                if (pathIndex == -1) {

                    String methodString = new String(content, 0, i);

                    try {

                        method = HttpMethod.valueOf(methodString);
                    }
                    catch(IllegalArgumentException e) {
                        throw new InvalidHttpMethodException("unknown HTTP method: \"" + methodString + "\"");
                    }
                    pathIndex = i + 1;
                    continue;
                }

                path = new String(content, pathIndex, i - pathIndex);
                versionIndex = i + 1;
                break;
            }
        }

        if (path == null) {

            if (pathIndex == -1) {
                throw new InvalidHttpRequestException("malformed request");
            }

            path = new String(content, pathIndex, content.length - pathIndex);
        }

        if (versionIndex == -1 || versionIndex >= content.length) {

            throw new InvalidHttpRequestException("missing HTTP version");
        }

        this.version = new String(content, versionIndex, rightLimit - versionIndex);

        if (version.trim().isEmpty()) {

            throw new InvalidHttpRequestException("missing HTTP version");
        }

        if (!"HTTP/1.1".equals(version)) {

            throw new InvalidHttpRequestException(version + " protocol version not supported");
        }
    }

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
