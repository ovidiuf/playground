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

package io.novaordis.playground.http.server.connection;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/8/17
 */
public class ConnectionTest {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    // Tests -----------------------------------------------------------------------------------------------------------

    // User-Agent ------------------------------------------------------------------------------------------------------

    @Test
    public void setUserAgent_FirstTimeAndSubsequentIdentical() throws Exception {

        Connection c = new Connection(0, new MockSocket(), false, null);

        assertNull(c.getUserAgent());

        c.setUserAgent("something");

        assertEquals("something", c.getUserAgent());

        c.setUserAgent("something");

        assertEquals("something", c.getUserAgent());
    }

    @Test
    public void setUserAgent_DifferentUserAgentValues() throws Exception {

        Connection c = new Connection(0, new MockSocket(), false, null);

        c.setUserAgent("something");

        assertEquals("something", c.getUserAgent());

        c.setUserAgent("something else");

        assertEquals("something, something else", c.getUserAgent());
    }

    // comparable ------------------------------------------------------------------------------------------------------

    @Test
    public void comparable_NotSameAge() throws Exception {

        Connection c = new Connection(0, new MockSocket(), false, null);
        c.setCreationTimestamp(10L);
        Connection c2 = new Connection(0, new MockSocket(), false, null);
        c2.setCreationTimestamp(20L);

        assertTrue(c.compareTo(c2) < 0);
        assertTrue(c2.compareTo(c) > 0);
    }

    @Test
    public void comparable_SameAge() throws Exception {

        Connection c = new Connection(0, new MockSocket(), false, null);
        c.setCreationTimestamp(10L);
        Connection c2 = new Connection(0, new MockSocket(), false, null);
        c2.setCreationTimestamp(10L);

        assertTrue(c.compareTo(c2) == 0);
        assertTrue(c2.compareTo(c) == 0);
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
