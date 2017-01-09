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

package io.novaordis.playground.http.server.http.header;

import io.novaordis.playground.http.server.http.HttpStatusCode;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/8/17
 */
public class HttpStatusCodeTest {

    // Constants -------------------------------------------------------------------------------------------------------

    //private static final Logger log = LoggerFactory.getLogger(HttpStatusCodeTest.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    // Tests -----------------------------------------------------------------------------------------------------------

    // fromString() ----------------------------------------------------------------------------------------------------

    @Test
    public void fromString_InvalidContent() throws Exception {

        HttpStatusCode c = HttpStatusCode.fromString("Something");
        assertNull(c);
    }

    @Test
    public void fromString_InvalidStatusCode() throws Exception {

        HttpStatusCode c = HttpStatusCode.fromString("600");
        assertNull(c);
    }

    @Test
    public void fromString_TrimRequired() throws Exception {

        HttpStatusCode sc = HttpStatusCode.fromString("   500 ");
        assertEquals(HttpStatusCode.INTERNAL_SERVER_ERROR, sc);
    }

    @Test
    public void fromString() throws Exception {

        HttpStatusCode sc = HttpStatusCode.fromString("200");
        assertEquals(HttpStatusCode.OK, sc);
    }

    // fromStatusLine() ------------------------------------------------------------------------------------------------

    @Test
    public void fromStatusLine_InvalidProtocolVersion() throws Exception {

        HttpStatusCode c = HttpStatusCode.fromStatusLine("HTTP/1.0 200 OK");
        assertNull(c);
    }

    @Test
    public void fromStatusLine_InvalidStatusCode() throws Exception {

        HttpStatusCode c = HttpStatusCode.fromStatusLine("HTTP/1.1 600 Something");
        assertNull(c);
    }

    @Test
    public void fromStatusLine() throws Exception {

        HttpStatusCode sc = HttpStatusCode.fromStatusLine("HTTP/1.1 200 OK");
        assertEquals(HttpStatusCode.OK, sc);
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
