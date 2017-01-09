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
public class HttpRequest extends MessageImpl {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

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
            throws ConnectionException, InvalidHttpMessageException {

        RequestBuffer buffer = new RequestBuffer();

        //
        // read up to the blank line - this will include the request line and all headers
        //

        boolean newLine = false;
        boolean lineFeedExpected = false;

        while(true) {

            int i = connection.read();

            if (i == -1) {

                //
                // we found the input stream closed on the first read, or after some irrelevant content has
                // accumulated in the buffer, return null to tell the upper layer that this connection has become
                // useless; it will probably close it
                //

                log.debug("reached client connection's EOS");

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

            if (lineFeedExpected) {

                if (i != '\n') {

                    throw new InvalidHttpHeaderException("missing LF");
                }

                lineFeedExpected = false;
            }


            if ('\r' == (char)i) {

                if (newLine) {

                    //
                    // blank line encountered, but loop one more time to consume the LF on the wire
                    //

                    lineFeedExpected = true;
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

        //
        // we identified the blank line - we can build the message headers and figure out whether there is follow-up
        // content, based on Content-Length header value
        //

        HttpRequest request = new HttpRequest(buffer.toByteArray());
        Integer contentLength = request.getContentLength();
        HttpMethod method = request.getMethod();

        if (contentLength == null) {

            //
            // no Content-Length, we should be OK if this is a GET, implicitly there is no body
            //

            if (HttpMethod.GET.equals(method)) {

                //
                // we're fine, we're done with the request
                //
                return request;
            }
            else if (HttpMethod.POST.equals(method)) {

                throw new InvalidHttpRequestException("POST request with no Content-Length");
            }
            else {

                throw new RuntimeException(
                        "NOT YET IMPLEMENTED: We don't know how to handle " + method + " without Content-Length");
            }
        }
        else {

            //
            // must read contentLength bytes
            //

            //
            // TODO timeout ?
            //

            int index = 0;
            byte[] body = new byte[contentLength];
            while(index < body.length) {

                int b = connection.read();

                if (b == -1) {

                    throw new RuntimeException("NOT YET IMPLEMENTED");
                }
                else {

                    body[index ++] = (byte)b;
                }
            }

            request.setBody(body);
        }

        return request;
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

    private byte[] wireFormat;

    private HttpMethod method;
    private String path;
    private String version;
    private Query query;

    // Constructors ----------------------------------------------------------------------------------------------------

    /**
     * Initializes common data structures.
     */
    private HttpRequest() {

        super();
    }

    /**
     * @param path (no query elements)
     */
    public HttpRequest(HttpMethod method, String path) throws InvalidHttpRequestException {

        this();
        this.method = method;

        if (path.contains("?")) {
            throw new IllegalArgumentException("the path contains a query section, which is not supported");
        }

        this.path = path;
        this.version = HttpServer.SUPPORTED_HTTP_VERSION;
        this.wireFormat = (method + " " + path + " " + version).getBytes();
        this.query = new Query();
    }

    /**
     * The header content, as read from the socket (with the exception that the CRs are filtered out; only the LF are
     * kept). Does not include the blank line.
     *
     * @exception InvalidHttpMessageException on faulty content that cannot be translated into a valid HTTP header
     */
    HttpRequest(byte[] wireFormat) throws InvalidHttpMessageException {

        this();
        this.wireFormat = wireFormat;

        int from = 0;

        for(int i = 0; i < wireFormat.length; i ++) {

            if (wireFormat[i] == '\r') {

                throw new IllegalArgumentException("the wire format has CR leftovers");
            }

            if (wireFormat[i] == '\n' || i == wireFormat.length - 1) {

                //
                // correction for the last element
                //
                i = i == wireFormat.length - 1 && wireFormat[i] != '\n' ? i + 1 : i;

                if (from == 0) {

                    parseFirstLine(wireFormat, from, i);
                }
                else {

                    HttpHeader h = HttpHeader.parseHeader(wireFormat, from, i);
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

    /**
     * @return the corresponding query parameter, if exists, or null otherwise.
     */
    public String getQueryParameter(String parameterName) {

        return query.getParameter(parameterName);
    }

    @Override
    public String toString() {

        if (wireFormat == null) {

            return "null wire format";
        }

        return getMethod() + " " + getPath() + " " + getHttpVersion();
    }

    // Package protected -----------------------------------------------------------------------------------------------

    /**
     * @param to the index of the first element <b>to ignore</b>. It may extend beyond the array boundary and the
     *           method implementation should be prepared to deal with this.
     */
    void parseFirstLine(byte[] wireFormat, int from, int to) throws InvalidHttpRequestException {

        int pathIndex = -1;
        int versionIndex = -1;
        int rightLimit = to >= wireFormat.length ? wireFormat.length : to;
        String pathWithQuery = null;

        for(int i = from; i < rightLimit; i ++) {

            if (wireFormat[i] == ' ') {

                if (pathIndex == -1) {

                    String methodString = new String(wireFormat, 0, i);

                    try {

                        method = HttpMethod.valueOf(methodString);
                    }
                    catch(IllegalArgumentException e) {
                        throw new InvalidHttpMethodException("unknown HTTP method: \"" + methodString + "\"");
                    }
                    pathIndex = i + 1;
                    continue;
                }

                pathWithQuery = new String(wireFormat, pathIndex, i - pathIndex);
                versionIndex = i + 1;
                break;
            }
        }

        if (pathWithQuery == null) {

            if (pathIndex == -1) {
                throw new InvalidHttpRequestException("malformed request");
            }

            pathWithQuery = new String(wireFormat, pathIndex, wireFormat.length - pathIndex);
        }

        if (versionIndex == -1 || versionIndex >= wireFormat.length) {

            throw new InvalidHttpRequestException("missing HTTP version");
        }

        this.version = new String(wireFormat, versionIndex, rightLimit - versionIndex);

        if (version.trim().isEmpty()) {

            throw new InvalidHttpRequestException("missing HTTP version");
        }

        if (!"HTTP/1.1".equals(version)) {

            throw new InvalidHttpRequestException(version + " protocol version not supported");
        }

        //
        // further parse the path to identify query parameters
        //
        parsePathWithQuery(pathWithQuery);
    }

    void parsePathWithQuery(String s) throws InvalidHttpRequestException {

        int i = s.indexOf('?');

        if (i == -1) {

            this.query = new Query();
            this.path = s;
        }
        else {

            this.query = new Query(s.substring(i + 1));
            this.path = s.substring(0, i);
        }
    }

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
