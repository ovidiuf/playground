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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/5/17
 */
public class HttpResponseTest extends HeadersTest {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(HttpResponseTest.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    @Test
    public void statusLineAndHeadersToWireFormat() throws Exception {

        HttpResponse r = new HttpResponse();
        r.setStatusCode(HttpStatusCode.OK);
        r.addHeader(HttpEntityHeader.CONTENT_LENGTH, "10");
        r.addHeader(HttpResponseHeader.SERVER, "NovaOrdis http-server");
        r.addHeader("Some-Random-Header", "something");

        byte[] b = r.statusLineAndHeadersToWireFormat();

        String s = new String(b);

        log.info(s);

        String expected =
                "HTTP/1.1 200 OK\r\n" +
                        "Content-Length: 10\r\n" +
                        "Server: NovaOrdis http-server\r\n" +
                        "Some-Random-Header: something\r\n" +
                        "\r\n";

        assertEquals(expected, s);
    }

    @Test
    public void setEntityBodyContent_InsureContentLengthIsSet() throws Exception {

        HttpResponse r = new HttpResponse();

        assertTrue(r.getHeaders().isEmpty());

        r.setEntityBodyContent("test".getBytes());

        //
        // make sure the content is set and the Content-Length is set
        //

        byte[] content = r.getEntityBodyContent();

        assertEquals("test", new String(content));

        List<HttpHeader> hs  = r.getHeader(HttpEntityHeader.CONTENT_LENGTH);
        assertEquals(1, hs.size());
        HttpHeader h = hs.get(0);

        assertEquals("4", h.getFieldBody());
    }

    @Test
    public void setEntityBodyContent_InsureContentLengthIsSet_NullBody() throws Exception {

        HttpResponse r = new HttpResponse();

        assertTrue(r.getHeaders().isEmpty());

        r.setEntityBodyContent(null);

        //
        // make sure the content is set and the Content-Length is set
        //

        byte[] content = r.getEntityBodyContent();

        assertNull(content);

        List<HttpHeader> hs  = r.getHeader(HttpEntityHeader.CONTENT_LENGTH);
        assertEquals(1, hs.size());
        HttpHeader h = hs.get(0);

        assertEquals("0", h.getFieldBody());
    }

    @Test
    public void setEntityBodyContent_InsureContentLengthIsSet_EmptyBody() throws Exception {

        HttpResponse r = new HttpResponse();

        assertTrue(r.getHeaders().isEmpty());

        r.setEntityBodyContent("".getBytes());

        //
        // make sure the content is set and the Content-Length is set
        //

        byte[] content = r.getEntityBodyContent();

        assertEquals("", new String(content));

        List<HttpHeader> hs  = r.getHeader(HttpEntityHeader.CONTENT_LENGTH);
        assertEquals(1, hs.size());
        HttpHeader h = hs.get(0);

        assertEquals("0", h.getFieldBody());
    }

    @Test
    public void setEntityBodyContent_InsureContentLengthIsSet_WhitespaceBody() throws Exception {

        HttpResponse r = new HttpResponse();

        assertTrue(r.getHeaders().isEmpty());

        r.setEntityBodyContent("  ".getBytes());

        //
        // make sure the content is set and the Content-Length is set
        //

        byte[] content = r.getEntityBodyContent();

        assertEquals("  ", new String(content));

        List<HttpHeader> hs  = r.getHeader(HttpEntityHeader.CONTENT_LENGTH);
        assertEquals(1, hs.size());
        HttpHeader h = hs.get(0);

        assertEquals("2", h.getFieldBody());
    }


    // Tests -----------------------------------------------------------------------------------------------------------

    // constructor -----------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    @Override
    protected HttpResponse getHeadersImplementationToTest() {

        return new HttpResponse();
    }

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
