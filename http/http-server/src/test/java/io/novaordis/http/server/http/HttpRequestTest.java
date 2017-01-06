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

package io.novaordis.http.server.http;

import io.novaordis.http.server.connection.MockConnection;
import io.novaordis.playground.http.server.http.HttpHeader;
import io.novaordis.playground.http.server.http.HttpMethod;
import io.novaordis.playground.http.server.http.HttpRequest;
import io.novaordis.playground.http.server.http.InvalidHttpMethodException;
import io.novaordis.playground.http.server.http.InvalidHttpRequestException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/5/17
 */
public class HttpRequestTest {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(HttpRequestTest.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    // Tests -----------------------------------------------------------------------------------------------------------

    // constructor -----------------------------------------------------------------------------------------------------

    @Test
    public void constructor_missingHttpVersion() throws Exception {

        try {

            new HttpRequest("GET /".getBytes());
            fail("should throw exception");
        }
        catch(InvalidHttpRequestException e) {

            String msg = e.getMessage();
            log.info(msg);
            assertEquals("missing HTTP version", msg);
        }
    }

    @Test
    public void constructor_missingHttpVersion2() throws Exception {

        try {

            new HttpRequest("GET / ".getBytes());
            fail("should throw exception");
        }
        catch(InvalidHttpRequestException e) {

            String msg = e.getMessage();
            log.info(msg);
            assertEquals("missing HTTP version", msg);
        }
    }

    @Test
    public void constructor_HTTP10NotSupported() throws Exception {

        try {

            new HttpRequest("GET / HTTP/1.0".getBytes());
            fail("should throw exception");
        }
        catch(InvalidHttpRequestException e) {

            String msg = e.getMessage();
            log.info(msg);
            assertEquals("HTTP/1.0 protocol version not supported", msg);
        }
    }

    @Test
    public void constructor_GET_InvalidMethod() throws Exception {

        try {
            new HttpRequest("blah / HTTP/1.1".getBytes());
            fail("should throw exception");
        }
        catch(InvalidHttpMethodException e) {

            String msg = e.getMessage();
            log.info(msg);
            assertEquals("unknown HTTP method: \"blah\"", msg);
        }
    }

    @Test
    public void constructor_GET_OneHeader() throws Exception {

        String requestContent =
                "GET /index.html HTTP/1.1\n" +
                        "Something: SomethingElse\n";

        HttpRequest r = new HttpRequest(requestContent.getBytes());

        assertEquals(HttpMethod.GET, r.getMethod());
        assertEquals("/index.html", r.getPath());
        assertEquals("HTTP/1.1", r.getHttpVersion());

        List<HttpHeader> headers = r.getHeaders();

        assertEquals(1, headers.size());

        HttpHeader h = r.getHeader("Something");

        assertEquals("Something", h.getFieldName());
        assertEquals("SomethingElse", h.getFieldBody());
    }

    @Test
    public void constructor_GET_MultipleHeaders() throws Exception {

        String requestContent =
                "GET /index.html HTTP/1.1\n" +
                        "Host: localhost:10000\n" +
                        "Upgrade-Insecure-Requests: 1\n" +
                        "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\n" +
                        "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/602.3.12 (KHTML, like Gecko) Version/10.0.2 Safari/602.3.12\n" +
                        "Accept-Language: en-us\n" +
                        "Accept-Encoding: gzip, deflate\n" +
                        "Connection: keep-alive\n";


        HttpRequest r = new HttpRequest(requestContent.getBytes());

        assertEquals(HttpMethod.GET, r.getMethod());
        assertEquals("/index.html", r.getPath());
        assertEquals("HTTP/1.1", r.getHttpVersion());

        List<HttpHeader> headers = r.getHeaders();

        assertEquals(7, headers.size());

        HttpHeader h = r.getHeader("Connection");

        assertEquals("Connection", h.getFieldName());
        assertEquals("keep-alive", h.getFieldBody());
    }

    // readRequest() ---------------------------------------------------------------------------------------------------

    @Test
    public void readRequest_missingHttpVersion() throws Exception {

        MockConnection mc = new MockConnection(0, "GET /\r\n");

        try {
            HttpRequest.readRequest(mc);
            fail("should throw exception");
        }
        catch(InvalidHttpRequestException e) {

            String msg = e.getMessage();
            log.info(msg);
            assertEquals("missing HTTP version", msg);
        }
    }

    @Test
    public void readRequest_missingHttpVersion2() throws Exception {

        MockConnection mc = new MockConnection(0, "GET / \r\n");

        try {
            HttpRequest.readRequest(mc);
            fail("should throw exception");
        }
        catch(InvalidHttpRequestException e) {

            String msg = e.getMessage();
            log.info(msg);
            assertEquals("missing HTTP version", msg);
        }
    }

    @Test
    public void readRequest_HTTP10NotSupported() throws Exception {

        String requestContent = "GET / HTTP/1.0\r\n";

        MockConnection mc = new MockConnection(0, requestContent);

        try {
            HttpRequest.readRequest(mc);
            fail("should throw exception");
        }
        catch(InvalidHttpRequestException e) {

            String msg = e.getMessage();
            log.info(msg);
            assertEquals("HTTP/1.0 protocol version not supported", msg);
        }
    }

    @Test
    public void readRequest_GET_InvalidMethod() throws Exception {

        String requestContent = "blah / HTTP/1.1\r\n";

        MockConnection mc = new MockConnection(0, requestContent);

        try {
            HttpRequest.readRequest(mc);
            fail("should throw exception");
        }
        catch(InvalidHttpMethodException e) {

            String msg = e.getMessage();
            log.info(msg);
            assertEquals("unknown HTTP method: \"blah\"", msg);
        }
    }

    @Test
    public void readRequest_GET() throws Exception {

        String requestContent =
                "GET /index.html HTTP/1.1\r\n" +
                        "Host: localhost:10000\r\n" +
                        "Upgrade-Insecure-Requests: 1\r\n" +
                        "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n" +
                        "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/602.3.12 (KHTML, like Gecko) Version/10.0.2 Safari/602.3.12\r\n" +
                        "Accept-Language: en-us\r\n" +
                        "Accept-Encoding: gzip, deflate\r\n" +
                        "Connection: keep-alive\r\n" +
                        "\r\n";


        MockConnection mc = new MockConnection(0, requestContent);
        HttpRequest r = HttpRequest.readRequest(mc);

        assertEquals(HttpMethod.GET, r.getMethod());
        assertEquals("/index.html", r.getPath());
        assertEquals("HTTP/1.1", r.getHttpVersion());

        List<HttpHeader> headers = r.getHeaders();

        assertEquals(7, headers.size());

        HttpHeader h = r.getHeader("Connection");

        assertEquals("Connection", h.getFieldName());
        assertEquals("keep-alive", h.getFieldBody());
    }

    @Test
    public void readRequest_POST() throws Exception {

        String requestContent =
                "POST /index.html HTTP/1.1\r\n" +
                        "Host: localhost:10000\r\n" +
                        "Upgrade-Insecure-Requests: 1\r\n" +
                        "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n" +
                        "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/602.3.12 (KHTML, like Gecko) Version/10.0.2 Safari/602.3.12\r\n" +
                        "Accept-Language: en-us\r\n" +
                        "Accept-Encoding: gzip, deflate\r\n" +
                        "Connection: keep-alive\r\n" +
                        "\r\n";

        MockConnection mc = new MockConnection(0, requestContent);
        HttpRequest r = HttpRequest.readRequest(mc);

        assertEquals(HttpMethod.POST, r.getMethod());

        fail("Return here");
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
