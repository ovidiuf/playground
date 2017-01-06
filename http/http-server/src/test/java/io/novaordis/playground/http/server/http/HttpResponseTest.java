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
import io.novaordis.playground.http.server.http.header.HttpResponseHeader;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/5/17
 */
public class HttpResponseTest extends MessageTest {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(HttpResponseTest.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    // constructor -----------------------------------------------------------------------------------------------------

    @Test
    public void constructor() throws Exception {

        HttpResponse r = new HttpResponse();

        assertNull(r.getStatusCode());

        List<HttpHeader> hs = r.getHeader(HttpEntityHeader.CONTENT_LENGTH);
        assertTrue(hs.isEmpty());
        assertEquals("HTTP/1.1", r.getHttpVersion());
        assertNull(r.getRequest());
        assertNull(r.getBody());
    }

    @Test
    public void constructor_StatusCode() throws Exception {

        HttpResponse r = new HttpResponse(HttpStatusCode.NOT_FOUND);

        assertEquals(HttpStatusCode.NOT_FOUND, r.getStatusCode());
        assertTrue(r.getHeaders().isEmpty());

        List<HttpHeader> hs = r.getHeader(HttpEntityHeader.CONTENT_LENGTH);
        assertTrue(hs.isEmpty());
        assertEquals("HTTP/1.1", r.getHttpVersion());
        assertNull(r.getRequest());
        assertNull(r.getBody());
    }

    @Test
    public void constructor_StatusCode_Body() throws Exception {

        HttpResponse r = new HttpResponse(HttpStatusCode.OK, "test".getBytes());

        assertEquals(HttpStatusCode.OK, r.getStatusCode());
        assertFalse(r.getHeaders().isEmpty());

        List<HttpHeader> hs;
        HttpHeader h;

        hs = r.getHeader(HttpEntityHeader.CONTENT_LENGTH);
        assertEquals(1, hs.size());
        h = hs.get(0);
        assertEquals(HttpEntityHeader.CONTENT_LENGTH, h.getHeaderDefinition());
        assertEquals("4", h.getFieldBody());

        assertEquals("HTTP/1.1", r.getHttpVersion());
        assertNull(r.getRequest());
        assertEquals("test", new String(r.getBody()));
    }

    // statusLineAndHeadersToWireFormat --------------------------------------------------------------------------------

    @Test
    public void statusLineAndHeadersToWireFormat() throws Exception {

        HttpResponse r = new HttpResponse();
        r.setStatusCode(HttpStatusCode.OK);
        r.addHeader(HttpResponseHeader.SERVER, "NovaOrdis http-server");
        r.addHeader("Some-Random-Header", "something");

        byte[] b = r.statusLineAndHeadersToWireFormat();

        String s = new String(b);

        log.info(s);

        String expected =
                "HTTP/1.1 200 OK\r\n" +
                        "Server: NovaOrdis http-server\r\n" +
                        "Some-Random-Header: something\r\n" +
                        "\r\n";

        assertEquals(expected, s);
    }

    // Tests -----------------------------------------------------------------------------------------------------------

    // constructor -----------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    @Override
    protected HttpResponse getMessageImplementationToTest() {

        return new HttpResponse();
    }

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
