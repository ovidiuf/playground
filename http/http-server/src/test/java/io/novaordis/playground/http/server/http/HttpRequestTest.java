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

import io.novaordis.playground.http.server.connection.MockConnection;
import io.novaordis.playground.http.server.http.header.HttpEntityHeader;
import io.novaordis.playground.http.server.http.header.HttpGeneralHeader;
import io.novaordis.playground.http.server.http.header.HttpHeader;
import io.novaordis.playground.http.server.http.header.HttpRequestHeader;
import io.novaordis.playground.http.server.http.header.InvalidHttpHeaderException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/5/17
 */
public class HttpRequestTest extends MessageTest {

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
    public void constructor_CommunicationCutShort() throws Exception {

        try {
            new HttpRequest("\n".getBytes());
            fail("should throw exception");
        }
        catch(InvalidHttpRequestException e) {

            String msg = e.getMessage();
            log.info(msg);
            assertEquals("malformed request", msg);
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

        headers = r.getHeader("Something");
        assertEquals(1, headers.size());

        assertEquals("Something", headers.get(0).getFieldName());
        assertEquals("SomethingElse", headers.get(0).getFieldBody());
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

        headers = r.getHeader("Connection");

        assertEquals(1, headers.size());

        assertEquals("Connection", headers.get(0).getFieldName());
        assertEquals("keep-alive", headers.get(0).getFieldBody());
    }

    // readRequest() GET -----------------------------------------------------------------------------------------------

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
    public void readRequest_InvalidMethod() throws Exception {

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

        assertNotNull(r);

        assertEquals(HttpMethod.GET, r.getMethod());
        assertEquals("/index.html", r.getPath());
        assertEquals("HTTP/1.1", r.getHttpVersion());

        List<HttpHeader> headers = r.getHeaders();

        assertEquals(7, headers.size());

        headers = r.getHeader("Connection");
        assertEquals(1, headers.size());

        assertEquals("Connection", headers.get(0).getFieldName());
        assertEquals("keep-alive", headers.get(0).getFieldBody());
    }

    @Test
    public void readRequest_GET_connectionClosedAbruptlyInMidstOfReadingARequest() throws Exception {

        MockConnection mc = new MockConnection(0, "HTT");

        try {
            HttpRequest.readRequest(mc);
            fail("should throw exception");
        }
        catch(InvalidHttpRequestException e) {

            String msg = e.getMessage();
            log.info(msg);
            assertEquals("malformed request", msg);
        }
    }

    @Test
    public void readRequest_GET_connectionClosedAbruptlyInMidstOfReadingARequest2() throws Exception {

        MockConnection mc = new MockConnection(0, "\nHTT");

        try {
            HttpRequest.readRequest(mc);
            fail("should throw exception");
        }
        catch(InvalidHttpRequestException e) {

            String msg = e.getMessage();
            log.info(msg);
            assertEquals("malformed request", msg);
        }
    }

    @Test
    public void readRequest_GET_inputStreamAtEOSToStartWith() throws Exception {

        MockConnection mc = new MockConnection(0);
        mc.close();
        assertTrue(mc.isClosed());

        HttpRequest request = HttpRequest.readRequest(mc);
        assertNull(request);
    }

    @Test
    public void readRequest_GET_inputStreamContainsOnlyDiscardableCharacters() throws Exception {

        MockConnection mc = new MockConnection(0, "    \r\n");
        HttpRequest request = HttpRequest.readRequest(mc);
        assertNull(request);
    }

    @Test
    public void readRequest_GET_makeSureTheLastLFIsConsumed() throws Exception {

        MockConnection mc = new MockConnection(0, "GET / HTTP/1.1\r\n\r\n");

        HttpRequest r = HttpRequest.readRequest(mc);

        assertNotNull(r);
        assertEquals(HttpMethod.GET, r.getMethod());
        assertEquals("/", r.getPath());
        assertEquals("HTTP/1.1", r.getHttpVersion());

        //
        // nothing should be left in the input stream
        //
        assertEquals(0, mc.bytesLeftToEOS());

        HttpRequest r2 = HttpRequest.readRequest(mc);
        assertNull(r2);
    }

    @Test
    public void readRequest_GET_missingLF() throws Exception {

        MockConnection mc = new MockConnection(0, "GET / HTTP/1.1\r\n\r");

        HttpRequest r = HttpRequest.readRequest(mc);

        assertNotNull(r);
        assertEquals(HttpMethod.GET, r.getMethod());
        assertEquals("/", r.getPath());
        assertEquals("HTTP/1.1", r.getHttpVersion());

        //
        // nothing should be left in the input stream
        //
        assertEquals(0, mc.bytesLeftToEOS());

        HttpRequest r2 = HttpRequest.readRequest(mc);
        assertNull(r2);
    }

    @Test
    public void readRequest_GET_nonLFCharacterWhenLFIsExpected() throws Exception {

        MockConnection mc = new MockConnection(0, "GET / HTTP/1.1\r\n\rx");

        try {

            HttpRequest.readRequest(mc);
            fail("should throw exception");
        }
        catch(InvalidHttpHeaderException e) {

            String msg = e.getMessage();
            log.info(msg);
            assertEquals("missing LF", msg);
        }
    }

    // readRequest() POST ----------------------------------------------------------------------------------------------

    @Test
    public void readRequest_POST_noContentLengthHeader() throws Exception {

        String requestContent =
                "POST /test HTTP/1.1\r\n" +
                        "Host: localhost:10000\r\n" +
                        "\r\n" +
                        "something without a content length";

        MockConnection mc = new MockConnection(requestContent);

        try {

            HttpRequest.readRequest(mc);
            fail("should throw exception");
        }
        catch(InvalidHttpRequestException e) {

            String msg = e.getMessage();
            log.info(msg);
            assertEquals("POST request with no Content-Length", msg);
        }
    }

    @Test
    public void readRequest_POST() throws Exception {

        String requestContent =
                "POST /test HTTP/1.1\r\n" +
                        "Host: localhost:10000\r\n" +
                        "Content-Type: application/x-www-form-urlencoded\r\n" +
                        "Origin: http://localhost:10000\r\n" +
                        "Content-Length: 24\r\n" +
                        "Connection: keep-alive\r\n" +
                        "Upgrade-Insecure-Requests: 1\r\n" +
                        "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n" +
                        "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/602.3.12 (KHTML, like Gecko) Version/10.0.2 Safari/602.3.12\r\n" +
                        "Referer: http://localhost:10000/form.html\r\n" +
                        "Accept-Language: en-us\r\n" +
                        "Accept-Encoding: gzip, deflate\r\n" +
                        "\r\n" +
                        "fname=Mickey&lname=Mouse";

        MockConnection mc = new MockConnection(requestContent);

        HttpRequest r = HttpRequest.readRequest(mc);

        assertNotNull(r);

        assertEquals(HttpMethod.POST, r.getMethod());
        assertEquals("/test", r.getPath());
        assertEquals("HTTP/1.1", r.getHttpVersion());

        List<HttpHeader> headers = r.getHeaders();
        assertEquals(11, headers.size());

        assertEquals(
                "localhost:10000",
                r.getHeader(HttpRequestHeader.HOST).get(0).getFieldBody());

        assertEquals(
                "application/x-www-form-urlencoded",
                r.getHeader(HttpEntityHeader.CONTENT_TYPE).get(0).getFieldBody());

        assertEquals(
                "http://localhost:10000",
                r.getHeader("Origin").get(0).getFieldBody());

        assertEquals(
                "24",
                r.getHeader(HttpEntityHeader.CONTENT_LENGTH).get(0).getFieldBody());

        assertEquals(
                "keep-alive",
                r.getHeader(HttpGeneralHeader.CONNECTION).get(0).getFieldBody());

        assertEquals(
                "1",
                r.getHeader("Upgrade-Insecure-Requests").get(0).getFieldBody());

        assertEquals(
                "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
                r.getHeader(HttpRequestHeader.ACCEPT).get(0).getFieldBody());

        assertEquals(
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/602.3.12 (KHTML, like Gecko) Version/10.0.2 Safari/602.3.12",
                r.getHeader(HttpRequestHeader.USER_AGENT).get(0).getFieldBody());

        assertEquals(
                "http://localhost:10000/form.html",
                r.getHeader(HttpRequestHeader.REFERER).get(0).getFieldBody());

        assertEquals(
                "en-us",
                r.getHeader(HttpRequestHeader.ACCEPT_LANGUAGE).get(0).getFieldBody());

        assertEquals(
                "gzip, deflate",
                r.getHeader(HttpRequestHeader.ACCEPT_ENCODING).get(0).getFieldBody());

        int contentLength = r.getContentLength();

        assertEquals(24, contentLength);

        byte[] body = r.getBody();

        assertNotNull(body);

        assertEquals(contentLength, body.length);

        assertEquals("fname=Mickey&lname=Mouse", new String(body));
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    @Override
    protected HttpRequest getMessageImplementationToTest() {

        return new HttpRequest(HttpMethod.GET, "/");
    }

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
