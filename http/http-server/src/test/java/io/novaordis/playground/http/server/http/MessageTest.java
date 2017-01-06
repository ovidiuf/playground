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
import io.novaordis.playground.http.server.http.header.HttpGeneralHeader;
import io.novaordis.playground.http.server.http.header.HttpHeader;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
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

    //
    // Header management -----------------------------------------------------------------------------------------------
    //

    @Test
    public void testHeaderNameCaseInsensitivity() throws Exception {

        Message m = getMessageImplementationToTest();

        m.addHeader(HttpGeneralHeader.CACHE_CONTROL, "something");

        assertNotEquals("cache-control", HttpGeneralHeader.CACHE_CONTROL.getCanonicalFieldName());

        List<HttpHeader> headers = m.getHeader("cache-control");
        assertEquals(1, headers.size());

        assertEquals(HttpGeneralHeader.CACHE_CONTROL, headers.get(0).getHeaderDefinition());
        assertEquals("something", headers.get(0).getFieldBody());
    }

    @Test
    public void testDuplicateNames() throws Exception {

        Message m = getMessageImplementationToTest();

        m.addHeader(HttpGeneralHeader.VIA, "1.1 host1:80");
        m.addHeader(HttpGeneralHeader.VIA, "1.1 host2:80");

        List<HttpHeader> headers = m.getHeader(HttpGeneralHeader.VIA);

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

        Message m = getMessageImplementationToTest();

        List<HttpHeader> headers = m.getHeader("something");
        assertTrue(headers.isEmpty());

        headers = m.getHeader(HttpGeneralHeader.VIA);
        assertTrue(headers.isEmpty());

        //
        // standard header
        //

        m.addHeader(HttpGeneralHeader.VIA, "HTTP/1.1 host1:80");

        headers = m.getHeader("something");
        assertTrue(headers.isEmpty());

        headers = m.getHeader(HttpGeneralHeader.VIA);
        assertEquals(1, headers.size());
        HttpHeader h = headers.get(0);
        assertEquals(HttpGeneralHeader.VIA, h.getHeaderDefinition());
        assertEquals("Via", h.getFieldName());
        assertEquals("HTTP/1.1 host1:80", h.getFieldBody());

        headers = m.getHeader("Via");
        assertEquals(1, headers.size());
        h = headers.get(0);
        assertEquals(HttpGeneralHeader.VIA, h.getHeaderDefinition());
        assertEquals("Via", h.getFieldName());
        assertEquals("HTTP/1.1 host1:80", h.getFieldBody());

        headers = m.getHeader("via");
        assertEquals(1, headers.size());
        h = headers.get(0);
        assertEquals(HttpGeneralHeader.VIA, h.getHeaderDefinition());
        assertEquals("Via", h.getFieldName());
        assertEquals("HTTP/1.1 host1:80", h.getFieldBody());

        //
        // non-standard header
        //

        m.addHeader("Some-Non-Standard-Header", "something");

        headers = m.getHeader("something");
        assertTrue(headers.isEmpty());

        headers = m.getHeader(HttpGeneralHeader.VIA);
        assertEquals(1, headers.size());
        h = headers.get(0);
        assertEquals(HttpGeneralHeader.VIA, h.getHeaderDefinition());
        assertEquals("Via", h.getFieldName());
        assertEquals("HTTP/1.1 host1:80", h.getFieldBody());

        headers = m.getHeader("Via");
        assertEquals(1, headers.size());
        h = headers.get(0);
        assertEquals(HttpGeneralHeader.VIA, h.getHeaderDefinition());
        assertEquals("Via", h.getFieldName());
        assertEquals("HTTP/1.1 host1:80", h.getFieldBody());

        headers = m.getHeader("via");
        assertEquals(1, headers.size());
        h = headers.get(0);
        assertEquals(HttpGeneralHeader.VIA, h.getHeaderDefinition());
        assertEquals("Via", h.getFieldName());
        assertEquals("HTTP/1.1 host1:80", h.getFieldBody());

        headers = m.getHeader("Some-Non-Standard-Header");
        assertEquals(1, headers.size());
        h = headers.get(0);
        assertNull(h.getHeaderDefinition());
        assertEquals("Some-Non-Standard-Header", h.getFieldName());
        assertEquals("something", h.getFieldBody());

        headers = m.getHeader("some-non-standard-header");
        assertEquals(1, headers.size());
        h = headers.get(0);
        assertNull(h.getHeaderDefinition());
        assertEquals("Some-Non-Standard-Header", h.getFieldName());
        assertEquals("something", h.getFieldBody());

        //
        // the second non-standard header
        //

        m.addHeader(new HttpHeader("Other-Header", "something else"));

        headers = m.getHeader("Other-Header");
        assertEquals(1, headers.size());
        h = headers.get(0);
        assertNull(h.getHeaderDefinition());
        assertEquals("Other-Header", h.getFieldName());
        assertEquals("something else", h.getFieldBody());

        headers = m.getHeader("other-header");
        assertEquals(1, headers.size());
        h = headers.get(0);
        assertNull(h.getHeaderDefinition());
        assertEquals("Other-Header", h.getFieldName());
        assertEquals("something else", h.getFieldBody());
    }

    @Test
    public void addNullHeader() throws Exception {

        Message m = getMessageImplementationToTest();

        try {

            m.addHeader(null);
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

        MessageImpl m = (MessageImpl) getMessageImplementationToTest();

        List<HttpHeader> hs = m.getHeader("Test-Header");
        assertTrue(hs.isEmpty());

        m.overwriteHeader(new HttpHeader("Test-Header", "test value 1"));

        hs = m.getHeader("Test-Header");
        assertEquals(1, hs.size());
        HttpHeader h = hs.get(0);

        assertEquals("Test-Header", h.getFieldName());
        assertEquals("test value 1", h.getFieldBody());

        //
        // overwrite
        //

        m.overwriteHeader(new HttpHeader("Test-Header", "test value 2"));

        hs = m.getHeader("Test-Header");
        assertEquals(1, hs.size());
        h = hs.get(0);

        assertEquals("Test-Header", h.getFieldName());
        assertEquals("test value 2", h.getFieldBody());
    }

    @Test
    public void overwriteHeader_CaseInsensitive() throws Exception {

        MessageImpl m = (MessageImpl) getMessageImplementationToTest();

        List<HttpHeader> hs = m.getHeader("test-header");
        assertTrue(hs.isEmpty());

        m.overwriteHeader(new HttpHeader("test-header", "test value 1"));

        hs = m.getHeader("test-header");
        assertEquals(1, hs.size());
        HttpHeader h = hs.get(0);

        assertEquals("test-header", h.getFieldName());
        assertEquals("test value 1", h.getFieldBody());

        //
        // overwrite, case insensitive
        //

        m.overwriteHeader(new HttpHeader("Test-Header", "test value 2"));

        hs = m.getHeader("Test-Header");
        assertEquals(1, hs.size());
        h = hs.get(0);

        assertEquals("Test-Header", h.getFieldName());
        assertEquals("test value 2", h.getFieldBody());

        hs = m.getHeader("test-header");
        assertEquals(1, hs.size());
        h = hs.get(0);

        assertEquals("Test-Header", h.getFieldName());
        assertEquals("test value 2", h.getFieldBody());
    }

    @Test
    public void overwriteHeader_MoreThanOneInternalCopy() throws Exception {

        MessageImpl m = (MessageImpl) getMessageImplementationToTest();

        List<HttpHeader> hs = m.getHeader("Test-Header");
        assertTrue(hs.isEmpty());

        m.addHeader("Test-Header", "test value 1");
        m.addHeader("Test-Header", "test value 2");

        hs = m.getHeader("Test-Header");
        assertEquals(2, hs.size());

        try {
            m.overwriteHeader(new HttpHeader("Test-Header", "does not matter"));
            fail("should throw exception");
        }
        catch(IllegalArgumentException e) {

            String msg = e.getMessage();
            log.info(msg);
            assertEquals("header \"Test-Header\" has more than one copy, cannot overwrite", msg);
        }
    }

    //
    // Body management -------------------------------------------------------------------------------------------------
    //

    // setEntityBodyContent --------------------------------------------------------------------------------------------

    @Test
    public void setBody_InsureContentLengthIsSet() throws Exception {

        Message m = getMessageImplementationToTest();

        assertNotNull(m.getHeader(HttpEntityHeader.CONTENT_LENGTH));

        m.setBody("test".getBytes());

        //
        // make sure the content is set and the Content-Length is set
        //

        byte[] content = m.getBody();
        assertEquals("test", new String(content));

        List<HttpHeader> hs  = m.getHeader(HttpEntityHeader.CONTENT_LENGTH);
        assertEquals(1, hs.size());
        HttpHeader h = hs.get(0);

        assertEquals("4", h.getFieldBody());
    }

    @Test
    public void setBody_InsureContentLengthIsSet_NullBody() throws Exception {

        Message m = getMessageImplementationToTest();

        m.setBody(null);

        //
        // make sure the content is set and the Content-Length is set
        //

        byte[] content = m.getBody();

        assertNull(content);

        List<HttpHeader> hs  = m.getHeader(HttpEntityHeader.CONTENT_LENGTH);
        assertEquals(1, hs.size());
        HttpHeader h = hs.get(0);

        assertEquals("0", h.getFieldBody());
    }

    @Test
    public void setBody_InsureContentLengthIsSet_EmptyBody() throws Exception {

        Message m = getMessageImplementationToTest();

        m.setBody("".getBytes());

        //
        // make sure the content is set and the Content-Length is set
        //

        byte[] content = m.getBody();

        assertEquals("", new String(content));

        List<HttpHeader> hs  = m.getHeader(HttpEntityHeader.CONTENT_LENGTH);
        assertEquals(1, hs.size());
        HttpHeader h = hs.get(0);

        assertEquals("0", h.getFieldBody());
    }

    @Test
    public void setBody_InsureContentLengthIsSet_WhitespaceBody() throws Exception {

        Message m = getMessageImplementationToTest();

        m.setBody("  ".getBytes());

        //
        // make sure the content is set and the Content-Length is set
        //

        byte[] content = m.getBody();

        assertEquals("  ", new String(content));

        List<HttpHeader> hs  = m.getHeader(HttpEntityHeader.CONTENT_LENGTH);
        assertEquals(1, hs.size());
        HttpHeader h = hs.get(0);

        assertEquals("2", h.getFieldBody());
    }


    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    protected abstract Message getMessageImplementationToTest();

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
