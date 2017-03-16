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

package io.novaordis.playground.java.network.traffic;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 3/15/17
 */
public class UtilTest {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    // Tests -----------------------------------------------------------------------------------------------------------

    // addressToBytes() ------------------------------------------------------------------------------------------------

    @Test
    public void addressToBytes_NoConversion() throws Exception {

        byte[] b = Util.addressToBytes("something");

        assertNull(b);
    }

    @Test
    public void addressToBytes() throws Exception {

        byte[] b = Util.addressToBytes("127.0.0.1");

        assertNotNull(b);

        assertEquals(4, b.length);

        assertEquals(127, b[0]);
        assertEquals(0, b[1]);
        assertEquals(0, b[2]);
        assertEquals(1, b[3]);
    }

    // identical() -----------------------------------------------------------------------------------------------------

    @Test
    public void identical() throws Exception {

        byte[] b = new byte[] {127, 0, 0, 1};
        byte[] b2 = new byte[] {127, 0, 0, 1};

        assertTrue(Util.identical(b, b2));
    }

    @Test
    public void identical_NotSo() throws Exception {

        byte[] b = new byte[] {127, 0, 0, 1};
        byte[] b2 = new byte[] {127, 0, 0, 2};

        assertFalse(Util.identical(b, b2));
    }

    @Test
    public void identical_NotSo_Length() throws Exception {

        byte[] b = new byte[] {127, 0, 0, 1, 2};
        byte[] b2 = new byte[] {127, 0, 0, 1};

        assertFalse(Util.identical(b, b2));
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
