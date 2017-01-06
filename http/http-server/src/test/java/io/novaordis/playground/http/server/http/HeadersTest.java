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

import io.novaordis.playground.http.server.http.header.HttpGeneralHeader;
import io.novaordis.playground.http.server.http.header.HttpHeader;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/6/17
 */
public abstract class HeadersTest {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(HeadersTest.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    // Tests -----------------------------------------------------------------------------------------------------------

    // headers ---------------------------------------------------------------------------------------------------------

    @Test
    public void testHeaderNameCaseInsensitivity() throws Exception {

        Headers r = getHeadersImplementationToTest();

        r.addHeader(HttpGeneralHeader.CACHE_CONTROL, "something");

        assertNotEquals("cache-control", HttpGeneralHeader.CACHE_CONTROL.getCanonicalFieldName());

        List<HttpHeader> headers = r.getHeader("cache-control");
        assertEquals(1, headers.size());

        assertEquals(HttpGeneralHeader.CACHE_CONTROL, headers.get(0).getHeaderDefinition());
        assertEquals("something", headers.get(0).getFieldBody());
    }

    @Test
    public void testDuplicateNames() throws Exception {

        Headers r = getHeadersImplementationToTest();

        r.addHeader(HttpGeneralHeader.VIA, "1.1 host1:80");
        r.addHeader(HttpGeneralHeader.VIA, "1.1 host2:80");

        List<HttpHeader> headers = r.getHeader(HttpGeneralHeader.VIA);

        assertEquals(2, headers.size());

        HttpHeader h = headers.get(0);
        assertEquals(HttpGeneralHeader.VIA, h.getHeaderDefinition());
        assertEquals("1.1 host1:80", h.getFieldBody());

        HttpHeader h2 = headers.get(1);
        assertEquals(HttpGeneralHeader.VIA, h2.getHeaderDefinition());
        assertEquals("1.1 host2:80", h2.getFieldBody());
    }

    @Test
    public void lifecycle() throws Exception {

        Headers r = getHeadersImplementationToTest();

        List<HttpHeader> headers = r.getHeaders();
        assertTrue(headers.isEmpty());

        headers = r.getHeader("something");
        assertTrue(headers.isEmpty());

        headers = r.getHeader(HttpGeneralHeader.VIA);
        assertTrue(headers.isEmpty());

        //
        // standard header
        //

        r.addHeader(HttpGeneralHeader.VIA, "HTTP/1.1 host1:80");

        headers = r.getHeaders();
        assertEquals(1, headers.size());
        HttpHeader h = headers.get(0);
        assertEquals(HttpGeneralHeader.VIA, h.getHeaderDefinition());
        assertEquals("Via", h.getFieldName());
        assertEquals("HTTP/1.1 host1:80", h.getFieldBody());

        headers = r.getHeader("something");
        assertTrue(headers.isEmpty());

        headers = r.getHeader(HttpGeneralHeader.VIA);
        assertEquals(1, headers.size());
        h = headers.get(0);
        assertEquals(HttpGeneralHeader.VIA, h.getHeaderDefinition());
        assertEquals("Via", h.getFieldName());
        assertEquals("HTTP/1.1 host1:80", h.getFieldBody());

        headers = r.getHeader("Via");
        assertEquals(1, headers.size());
        h = headers.get(0);
        assertEquals(HttpGeneralHeader.VIA, h.getHeaderDefinition());
        assertEquals("Via", h.getFieldName());
        assertEquals("HTTP/1.1 host1:80", h.getFieldBody());

        headers = r.getHeader("via");
        assertEquals(1, headers.size());
        h = headers.get(0);
        assertEquals(HttpGeneralHeader.VIA, h.getHeaderDefinition());
        assertEquals("Via", h.getFieldName());
        assertEquals("HTTP/1.1 host1:80", h.getFieldBody());

        //
        // non-standard header
        //

        r.addHeader("Some-Non-Standard-Header", "something");

        headers = r.getHeaders();
        assertEquals(2, headers.size());
        h = headers.get(0);
        assertEquals(HttpGeneralHeader.VIA, h.getHeaderDefinition());
        assertEquals("Via", h.getFieldName());
        assertEquals("HTTP/1.1 host1:80", h.getFieldBody());
        HttpHeader h2 = headers.get(1);
        assertNull(h2.getHeaderDefinition());
        assertEquals("Some-Non-Standard-Header", h2.getFieldName());
        assertEquals("something", h2.getFieldBody());

        headers = r.getHeader("something");
        assertTrue(headers.isEmpty());

        headers = r.getHeader(HttpGeneralHeader.VIA);
        assertEquals(1, headers.size());
        h = headers.get(0);
        assertEquals(HttpGeneralHeader.VIA, h.getHeaderDefinition());
        assertEquals("Via", h.getFieldName());
        assertEquals("HTTP/1.1 host1:80", h.getFieldBody());

        headers = r.getHeader("Via");
        assertEquals(1, headers.size());
        h = headers.get(0);
        assertEquals(HttpGeneralHeader.VIA, h.getHeaderDefinition());
        assertEquals("Via", h.getFieldName());
        assertEquals("HTTP/1.1 host1:80", h.getFieldBody());

        headers = r.getHeader("via");
        assertEquals(1, headers.size());
        h = headers.get(0);
        assertEquals(HttpGeneralHeader.VIA, h.getHeaderDefinition());
        assertEquals("Via", h.getFieldName());
        assertEquals("HTTP/1.1 host1:80", h.getFieldBody());

        headers = r.getHeader("Some-Non-Standard-Header");
        assertEquals(1, headers.size());
        h = headers.get(0);
        assertNull(h.getHeaderDefinition());
        assertEquals("Some-Non-Standard-Header", h.getFieldName());
        assertEquals("something", h.getFieldBody());

        headers = r.getHeader("some-non-standard-header");
        assertEquals(1, headers.size());
        h = headers.get(0);
        assertNull(h.getHeaderDefinition());
        assertEquals("Some-Non-Standard-Header", h.getFieldName());
        assertEquals("something", h.getFieldBody());

        //
        // the second non-standard header
        //

        r.addHeader(new HttpHeader("Other-Header", "something else"));

        headers = r.getHeaders();
        assertEquals(3, headers.size());
        h = headers.get(0);
        assertEquals(HttpGeneralHeader.VIA, h.getHeaderDefinition());
        assertEquals("Via", h.getFieldName());
        assertEquals("HTTP/1.1 host1:80", h.getFieldBody());
        h2 = headers.get(1);
        assertNull(h2.getHeaderDefinition());
        assertEquals("Some-Non-Standard-Header", h2.getFieldName());
        assertEquals("something", h2.getFieldBody());
        HttpHeader h3 = headers.get(2);
        assertNull(h3.getHeaderDefinition());
        assertEquals("Other-Header", h3.getFieldName());
        assertEquals("something else", h3.getFieldBody());

        headers = r.getHeader("Other-Header");
        assertEquals(1, headers.size());
        h = headers.get(0);
        assertNull(h.getHeaderDefinition());
        assertEquals("Other-Header", h.getFieldName());
        assertEquals("something else", h.getFieldBody());

        headers = r.getHeader("other-header");
        assertEquals(1, headers.size());
        h = headers.get(0);
        assertNull(h.getHeaderDefinition());
        assertEquals("Other-Header", h.getFieldName());
        assertEquals("something else", h.getFieldBody());
    }

    @Test
    public void addNullHeader() throws Exception {

        Headers r = getHeadersImplementationToTest();

        try {
            r.addHeader(null);
            fail("should have thrown exception");
        } catch (IllegalArgumentException e) {

            String msg = e.getMessage();
            log.info(msg);
            assertEquals("null header", msg);
        }
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    protected abstract Headers getHeadersImplementationToTest();

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
