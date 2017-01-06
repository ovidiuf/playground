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

import io.novaordis.playground.http.server.http.HttpHeader;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/5/17
 */
public class HttpHeaderTest {

    // Constants -------------------------------------------------------------------------------------------------------
    
    private static final Logger log = LoggerFactory.getLogger(HttpHeaderTest.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    // Tests -----------------------------------------------------------------------------------------------------------

    @Test
    public void parseHeader_BeyondBoundary() throws Exception {

        byte[] content = new byte[] {'A', ':', 'B'};

        HttpHeader h = HttpHeader.parseHeader(content, 0, 100);

        assertEquals("A", h.getFieldName());
        assertEquals("B", h.getFieldBody());
    }

    @Test
    public void parseHeader_BeyondBoundary2() throws Exception {

        byte[] content = new byte[] {'A', ':', 'B'};

        HttpHeader h = HttpHeader.parseHeader(content, 0, 3);

        assertEquals("A", h.getFieldName());
        assertEquals("B", h.getFieldBody());
    }

    @Test
    public void parseHeader_OnBoundary() throws Exception {

        byte[] content = new byte[] {'A', ':', 'B', '\n'};

        HttpHeader h = HttpHeader.parseHeader(content, 0, 3);

        assertEquals("A", h.getFieldName());
        assertEquals("B", h.getFieldBody());
    }

    @Test
    public void parseHeader() throws Exception {

        byte[] content = new byte[] {'A', ':', 'B', '\n', 'X'};

        HttpHeader h = HttpHeader.parseHeader(content, 0, 3);

        assertEquals("A", h.getFieldName());
        assertEquals("B", h.getFieldBody());

        log.info(h.toString());
    }

    @Test
    public void parseHeader_NoColon() throws Exception {

        byte[] content = new byte[] {'A', 'B', 'C'};

        HttpHeader h = HttpHeader.parseHeader(content, 0, 1);
        assertEquals("A", h.getFieldName());
        assertNull(h.getFieldBody());

        log.info(h.toString());
    }

    @Test
    public void parseHeader_EmptyBody() throws Exception {

        byte[] content = new byte[] {'A', ':'};

        HttpHeader h = HttpHeader.parseHeader(content, 0, 2);

        assertEquals("A", h.getFieldName());
        assertEquals("", h.getFieldBody());

        log.info(h.toString());
    }

    @Test
    public void parseHeader_BodyRequiresTrimming() throws Exception {

        byte[] content = new byte[] {'A', ':', ' ', ' ', 'B'};

        HttpHeader h = HttpHeader.parseHeader(content, 0, 100);

        assertEquals("A", h.getFieldName());
        assertEquals("B", h.getFieldBody());
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
