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
public abstract class MessageTest {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(MessageTest.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    // Tests -----------------------------------------------------------------------------------------------------------

    // headers ---------------------------------------------------------------------------------------------------------

    @Test
    public void testHeaderNameCaseInsensitivity() throws Exception {

        Message r = getMessageImplementationToTest();

        r.addHeader(HttpGeneralHeader.CACHE_CONTROL, "something");

        assertNotEquals("cache-control", HttpGeneralHeader.CACHE_CONTROL.getCanonicalFieldName());

        List<HttpHeader> headers = r.getHeader("cache-control");
        assertEquals(1, headers.size());

        assertEquals(HttpGeneralHeader.CACHE_CONTROL, headers.get(0).getHeaderDefinition());
        assertEquals("something", headers.get(0).getFieldBody());
    }

    @Test
    public void testDuplicateNames() throws Exception {

        Message r = getMessageImplementationToTest();

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

        Message r = getMessageImplementationToTest();

        List<HttpHeader> headers = r.getHeader("something");
        assertTrue(headers.isEmpty());

        headers = r.getHeader(HttpGeneralHeader.VIA);
        assertTrue(headers.isEmpty());

        //
        // standard header
        //

        r.addHeader(HttpGeneralHeader.VIA, "HTTP/1.1 host1:80");

        headers = r.getHeader("something");
        assertTrue(headers.isEmpty());

        headers = r.getHeader(HttpGeneralHeader.VIA);
        assertEquals(1, headers.size());
        HttpHeader h = headers.get(0);
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

        Message r = getMessageImplementationToTest();

        try {
            r.addHeader(null);
            fail("should have thrown exception");
        } catch (IllegalArgumentException e) {

            String msg = e.getMessage();
            log.info(msg);
            assertEquals("null header", msg);
        }
    }

    // overwriteHeader() -----------------------------------------------------------------------------------------------

    @Test
    public void overwriteHeader() throws Exception {

        MessageImpl r = (MessageImpl) getMessageImplementationToTest();

        List<HttpHeader> hs = r.getHeader("Test-Header");
        assertTrue(hs.isEmpty());

        r.overwriteHeader(new HttpHeader("Test-Header", "test value 1"));

        hs = r.getHeader("Test-Header");
        assertEquals(1, hs.size());
        HttpHeader h = hs.get(0);

        assertEquals("Test-Header", h.getFieldName());
        assertEquals("test value 1", h.getFieldBody());

        //
        // overwrite
        //

        r.overwriteHeader(new HttpHeader("Test-Header", "test value 2"));

        hs = r.getHeader("Test-Header");
        assertEquals(1, hs.size());
        h = hs.get(0);

        assertEquals("Test-Header", h.getFieldName());
        assertEquals("test value 2", h.getFieldBody());
    }

    @Test
    public void overwriteHeader_CaseInsensitive() throws Exception {

        MessageImpl r = (MessageImpl) getMessageImplementationToTest();

        List<HttpHeader> hs = r.getHeader("test-header");
        assertTrue(hs.isEmpty());

        r.overwriteHeader(new HttpHeader("test-header", "test value 1"));

        hs = r.getHeader("test-header");
        assertEquals(1, hs.size());
        HttpHeader h = hs.get(0);

        assertEquals("test-header", h.getFieldName());
        assertEquals("test value 1", h.getFieldBody());

        //
        // overwrite, case insensitive
        //

        r.overwriteHeader(new HttpHeader("Test-Header", "test value 2"));

        hs = r.getHeader("Test-Header");
        assertEquals(1, hs.size());
        h = hs.get(0);

        assertEquals("Test-Header", h.getFieldName());
        assertEquals("test value 2", h.getFieldBody());

        hs = r.getHeader("test-header");
        assertEquals(1, hs.size());
        h = hs.get(0);

        assertEquals("Test-Header", h.getFieldName());
        assertEquals("test value 2", h.getFieldBody());
    }

    @Test
    public void overwriteHeader_MoreThanOneInternalCopy() throws Exception {

        MessageImpl r = (MessageImpl) getMessageImplementationToTest();

        List<HttpHeader> hs = r.getHeader("Test-Header");
        assertTrue(hs.isEmpty());

        r.addHeader("Test-Header", "test value 1");
        r.addHeader("Test-Header", "test value 2");

        hs = r.getHeader("Test-Header");
        assertEquals(2, hs.size());

        try {
            r.overwriteHeader(new HttpHeader("Test-Header", "does not matter"));
            fail("should throw exception");
        }
        catch(IllegalArgumentException e) {

            String msg = e.getMessage();
            log.info(msg);
            assertEquals("header \"Test-Header\" has more than one copy, cannot overwrite", msg);
        }

    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    protected abstract Message getMessageImplementationToTest();

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
