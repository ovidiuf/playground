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
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/6/17
 */
public class ConnectionHandlerTest {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    // Tests -----------------------------------------------------------------------------------------------------------

    @Test
    public void sendResponse_CloseAfterHandlingConnection() throws Exception {

        MockConnection mc = new MockConnection(0);
        ConnectionHandler h = new ConnectionHandler(new MockServer(), mc);

        h.closeConnectionAfterResponse(true);

        HttpResponse r = new HttpResponse();

        r.setStatusCode(HttpStatusCode.OK);
        r.addHeader("Some-Header", "some value");
        r.setEntityBodyContent("test\n".getBytes());
        r.addHeader("Some-Header-2", "some value 2");

        h.sendResponse(r);

        List<byte[]> flushedContent = mc.getFlushedContent();

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

        assertTrue(mc.isClosed());
    }

    @Test
    public void sendResponse_DoNotCloseAfterHandlingConnection() throws Exception {

        MockConnection mc = new MockConnection(0);
        ConnectionHandler h = new ConnectionHandler(new MockServer(), mc);

        h.closeConnectionAfterResponse(false);

        HttpResponse r = new HttpResponse();

        r.setStatusCode(HttpStatusCode.OK);
        r.addHeader("Some-Header", "some value");
        r.setEntityBodyContent("test\n".getBytes());
        r.addHeader("Some-Header-2", "some value 2");

        h.sendResponse(r);

        List<byte[]> flushedContent = mc.getFlushedContent();

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
