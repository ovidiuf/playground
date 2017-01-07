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

import io.novaordis.playground.http.server.connection.MockConnection;
import io.novaordis.playground.http.server.http.HttpResponse;
import io.novaordis.playground.http.server.http.HttpStatusCode;
import io.novaordis.playground.http.server.http.header.HttpEntityHeader;
import io.novaordis.playground.http.server.http.header.HttpHeader;
import io.novaordis.playground.http.server.http.header.HttpResponseHeader;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/6/17
 */
public class ConnectionHandlerTest {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(ConnectionHandlerTest.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    // Tests -----------------------------------------------------------------------------------------------------------

    // connection handling top level loop ------------------------------------------------------------------------------

    @Test
    public void connectionHandlingTopLevelLoop_ShouldExitImmediately() throws Exception {

        MockServer ms = new MockServer();
        MockConnection mc = new MockConnection(0, "   ");
        MockRequestHandler mh = new MockRequestHandler(); // returns a 200 OK for any request
        ms.addHandler(mh);

        ConnectionHandler ch = new ConnectionHandler(ms, mc);

        ch.run();

        assertFalse(ch.isActive());

        //
        // make sure no response was written on the wire
        //
        assertTrue(mc.getFlushedOutputContent().isEmpty());

        //
        // the socket input stream reached EOS, nothing is going to come over that connection, make sure the connection
        // is closed
        //
        assertTrue(mc.isClosed());
    }

    @Test
    public void connectionHandlingTopLevelLoop_ShouldExitAfterARequestResponsePair() throws Exception {

        MockServer ms = new MockServer();
        MockConnection mc = new MockConnection(0, "GET / HTTP/1.1\r\n\r\n");
        MockRequestHandler mh = new MockRequestHandler(); // returns a 200 OK for any request
        ms.addHandler(mh);

        ConnectionHandler ch = new ConnectionHandler(ms, mc);


        ch.run();

        assertFalse(ch.isActive());

        //
        // make sure the OK response was written on the wire (no body)
        //
        List<byte[]> content = mc.getFlushedOutputContent();
        assertEquals(1, content.size()); // no body
        String statusLineAndHeaders = new String(content.get(0));

        log.info(statusLineAndHeaders);
        assertTrue(statusLineAndHeaders.startsWith("HTTP/1.1 200 OK"));

        //
        // the socket input stream reached EOS, nothing is going to come over that connection, make sure the connection
        // is closed
        //
        assertTrue(mc.isClosed());
    }

    @Test
    public void connectionHandlingTopLevelLoop_ShouldBlock() throws Exception {

        String inputStreamContent = "GET / HTTP/1.1\r\n\r\n";
        MockServer ms = new MockServer();
        MockConnection mc = new MockConnection(0, inputStreamContent);
        //
        // we block after inputStreamContent.length() characters are read, which is right after the first request
        //
        final CountDownLatch readBlocked = new CountDownLatch(1);
        final CountDownLatch readReleaseLatch = new CountDownLatch(1);
        mc.blockInReadingAfterTheSpecifiedNumberOfCharacters(
                inputStreamContent.length(), readBlocked, readReleaseLatch);

        MockRequestHandler mh = new MockRequestHandler(); // returns a 200 OK for any request
        ms.addHandler(mh);

        ConnectionHandler ch = new ConnectionHandler(ms, mc);

        //
        // perform reading from a separate thread
        //

        new Thread(() -> {

            //noinspection Convert2MethodRef
            ch.run();

        }, "mock connection handling thread").start();

        //
        // wait for the read to block
        //

        readBlocked.await();

        log.info("mock connection handling thread blocked in read()");

        assertTrue(ch.isActive());

        //
        // make sure the OK response was written on the wire (no body)
        //
        List<byte[]> content = mc.getFlushedOutputContent();
        assertEquals(1, content.size()); // no body
        String statusLineAndHeaders = new String(content.get(0));

        log.info("status line and headers of the response wrote to connection:\n" + statusLineAndHeaders);
        assertTrue(statusLineAndHeaders.startsWith("HTTP/1.1 200 OK"));

        //
        // release the mock connection handling thread
        //

        readReleaseLatch.countDown();

        //
        // loop until the connection is closed
        //
        long t0 = System.currentTimeMillis();
        long timeout = 1000L;
        while(true) {

            if (System.currentTimeMillis() - t0 > timeout) {
                fail("connection was not closed after more than " + timeout + " ms");
            }

            if (mc.isClosed()) {
                break;
            }
        }

        assertTrue(mc.isClosed());
    }

    // processRequestResponsePair() ------------------------------------------------------------------------------------

    @Test
    public void processRequestResponsePair_ConnectionClosed() throws Exception {

        MockConnection mc = new MockConnection(0);
        mc.close();
        assertTrue(mc.isClosed());

        ConnectionHandler h = new ConnectionHandler(new MockServer(), mc);

        boolean moreRequests = h.processRequestResponsePair();
        assertFalse(moreRequests);
    }

    @Test
    public void processRequestResponsePair_InputStreamContainsDiscardables() throws Exception {

        MockConnection mc = new MockConnection(0, "      \r\n");

        ConnectionHandler h = new ConnectionHandler(new MockServer(), mc);

        boolean moreRequests = h.processRequestResponsePair();
        assertFalse(moreRequests);
    }

    @Test
    public void processRequestResponsePair_InvalidRequest() throws Exception {

        MockConnection mc = new MockConnection(0, "NO_SUCH_METHOD / HTTP/1.1\r\n\r\n");

        ConnectionHandler h = new ConnectionHandler(new MockServer(), mc);

        boolean moreRequests = h.processRequestResponsePair();
        assertTrue(moreRequests);
    }

    @Test
    public void processRequestResponsePair() throws Exception {

        MockConnection mc = new MockConnection(0, "HTTP/1.1 GET /\r\n\r\n");

        ConnectionHandler h = new ConnectionHandler(new MockServer(), mc);

        boolean moreRequests = h.processRequestResponsePair();
        assertTrue(moreRequests);
    }

    // prepareResponseForSending() -------------------------------------------------------------------------------------

    @Test
    public void prepareResponseForSending() throws Exception {

        ConnectionHandler ch = new ConnectionHandler(new MockServer(), new MockConnection());

        HttpResponse r = new HttpResponse();

        assertTrue(r.getHeader(HttpEntityHeader.CONTENT_LENGTH).isEmpty());
        assertTrue(r.getHeader(HttpResponseHeader.SERVER).isEmpty());

        HttpResponse r2 = ch.prepareResponseForSending(r);

        assertTrue(r2 == r);

        //
        // we insure that before sending the request has been prepared for sending (SERVER, CONTENT_LENGTH)
        //

        List<HttpHeader> hs = r.getHeader(HttpEntityHeader.CONTENT_LENGTH);
        assertEquals(1, hs.size());
        HttpHeader h = hs.get(0);
        assertEquals("0", h.getFieldBody());

        hs = r.getHeader(HttpResponseHeader.SERVER);
        assertEquals(1, hs.size());
        h = hs.get(0);
        assertEquals("Mock", h.getFieldBody());
    }

    // sendResponse() --------------------------------------------------------------------------------------------------

    @Test
    public void sendResponse_CloseAfterHandlingConnection() throws Exception {

        MockConnection mc = new MockConnection(0);
        ConnectionHandler h = new ConnectionHandler(new MockServer(), mc);

        h.setCloseConnectionAfterResponse(true);

        HttpResponse r = new HttpResponse();

        r.setStatusCode(HttpStatusCode.OK);
        r.addHeader("Some-Header", "some value");
        r.setBody("test\n".getBytes());
        r.addHeader("Some-Header-2", "some value 2");

        h.sendResponse(r);

        List<byte[]> flushedContent = mc.getFlushedOutputContent();

        // two flushes
        assertEquals(2, flushedContent.size());

        byte[] statusLineAndHeaders = flushedContent.get(0);
        byte[] body = flushedContent.get(1);

        String expectedStatusLineAndHeaders =
                "HTTP/1.1 200 OK\r\n" +
                        "Some-Header: some value\r\n" +
                        "Content-Length: 5\r\n" +
                        "Some-Header-2: some value 2\r\n" +
                        "\r\n";

        String actual = new String(statusLineAndHeaders);
        log.info(actual);
        assertEquals(expectedStatusLineAndHeaders, actual);

        String expectedBody = "test\n";

        assertEquals(expectedBody, new String(body));

        assertTrue(mc.isClosed());
    }

    @Test
    public void sendResponse_DoNotCloseAfterHandlingConnection() throws Exception {

        MockConnection mc = new MockConnection(0);
        ConnectionHandler h = new ConnectionHandler(new MockServer(), mc);

        h.setCloseConnectionAfterResponse(false);

        HttpResponse r = new HttpResponse();

        r.setStatusCode(HttpStatusCode.OK);
        r.addHeader("Some-Header", "some value");
        r.setBody("test\n".getBytes());
        r.addHeader("Some-Header-2", "some value 2");

        h.sendResponse(r);

        List<byte[]> flushedContent = mc.getFlushedOutputContent();

        // two flushes
        assertEquals(2, flushedContent.size());

        byte[] statusLineAndHeaders = flushedContent.get(0);
        byte[] body = flushedContent.get(1);

        String expectedStatusLineAndHeaders =
                "HTTP/1.1 200 OK\r\n" +
                        "Some-Header: some value\r\n" +
                        "Content-Length: 5\r\n" +
                        "Some-Header-2: some value 2\r\n" +
                        "\r\n";

        assertEquals(expectedStatusLineAndHeaders, new String(statusLineAndHeaders));

        String expectedBody = "test\n";

        assertEquals(expectedBody, new String(body));

        assertFalse(mc.isClosed());
    }


    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
