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

import java.net.InetAddress;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 3/15/17
 */
public class ConfigurationTest {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    // Tests -----------------------------------------------------------------------------------------------------------

    @Test
    public void constructor_send() throws Exception {

        String[] args = {

                "send",
                "--protocol=multicast",
                "--interface=eth1",
                "--address=127.0.0.1",
                "--port=5555"

        };

        Configuration c = new Configuration(args);

        assertEquals(Mode.send, c.getMode());
        assertEquals(Protocol.multicast, c.getProtocol());
        assertEquals("127.0.0.1", c.getAddress());
        assertEquals("eth1", c.getInterface());
        assertEquals(5555, c.getPort().intValue());
    }

    @Test
    public void constructor_receive() throws Exception {

        String[] args = {

                "receive",
                "--protocol=multicast",
                "--interface=eth1",
                "--port=5555"
        };

        Configuration c = new Configuration(args);

        assertEquals(Mode.receive, c.getMode());
        assertEquals(Protocol.multicast, c.getProtocol());
        assertNull(c.getAddress());
        assertEquals("eth1", c.getInterface());
        assertEquals(5555, c.getPort().intValue());
    }

    @Test
    public void constructor_unknownMode() throws Exception {

        String[] args = {

                "something",
                "--protocol=multicast",
                "--interface=eth1",
                "--port=5555"
        };

        try {

            new Configuration(args);
            fail("should throw exception");
        }
        catch(UserErrorException e) {

            String msg = e.getMessage();
            assertTrue(msg.contains("unknown mode"));
        }
    }

    @Test
    public void constructor_missingMode() throws Exception {

        String[] args = new String[0];

        try {

            new Configuration(args);
            fail("should throw exception");
        }
        catch(UserErrorException e) {

            String msg = e.getMessage();
            assertTrue(msg.contains("missing mode"));
        }
    }


    @Test
    public void constructor_portNotAnInt() throws Exception {

        String[] args = {

                "receive",
                "--protocol=multicast",
                "--interface=eth1",
                "--port=blah"
        };

        try {

            new Configuration(args);
            fail("should throw exception");
        }
        catch(UserErrorException e) {

            String msg = e.getMessage();
            assertTrue(msg.contains("invalid port value"));
            assertTrue(msg.contains("not an integer"));
        }
    }

    @Test
    public void constructor_LocalAddressAndPort() throws Exception {

        String[] args = {

                "send",
                "--protocol=multicast",
                "--address=127.0.0.1",
                "--port=5555",
                "--local-address=127.0.0.2",
                "--local-port=6666",
        };

        Configuration c = new Configuration(args);

        assertEquals("127.0.0.1", c.getAddress());
        assertNull(c.getInetAddress());
        assertEquals(5555, c.getPort().intValue());

        assertEquals("127.0.0.2", c.getLocalAddress());
        assertNull(c.getLocalInetAddress());
        assertEquals(6666, c.getLocalPort().intValue());

        c.validate();

        assertEquals(InetAddress.getByAddress(new byte[] {127, 0, 0, 1}), c.getInetAddress());
        assertEquals(InetAddress.getByAddress(new byte[] {127, 0, 0, 2}), c.getLocalInetAddress());

    }

    // validatePort() --------------------------------------------------------------------------------------------------

    @Test
    public void validatePort() throws Exception {

        Configuration c = new Configuration();

        try {

            c.validatePort();
            fail("should throw exception");
        }
        catch(UserErrorException e) {

            String msg = e.getMessage();
            assertTrue(msg.contains("a port must be specified"));
        }
    }

    @Test
    public void validatePort_OutOfRange_Left() throws Exception {

        Configuration c = new Configuration();

        c.setPort(-1);

        try {

            c.validatePort();
            fail("should throw exception");
        }
        catch(UserErrorException e) {

            String msg = e.getMessage();
            assertTrue(msg.contains("port out of range"));
        }
    }

    @Test
    public void validatePort_OutOfRange_Right() throws Exception {

        Configuration c = new Configuration();

        c.setPort(65536);

        try {

            c.validatePort();
            fail("should throw exception");
        }
        catch(UserErrorException e) {

            String msg = e.getMessage();
            assertTrue(msg.contains("port out of range"));
        }
    }

    // initializeAndValidateAddress() ----------------------------------------------------------------------------------

    @Test
    public void initializeAndValidateAddress_MissingIsFine() throws Exception {

        Configuration c = new Configuration();

        assertNull(c.getAddress());

        c.initializeAndValidateAddress(false);

        assertNull(c.getAddress());
        assertNull(c.getInetAddress());
        assertNull(c.getPort());
    }

    @Test
    public void initializeAndValidateAddress_MissingIsNotFine() throws Exception {

        Configuration c = new Configuration();

        assertNull(c.getAddress());

        try {

            c.initializeAndValidateAddress(true);
            fail("should throw exception");
        }
        catch(UserErrorException e) {

            String msg = e.getMessage();
            assertTrue(msg.contains("missing required address"));
        }
    }

    @Test
    public void initializeAndValidateAddress_DoesIncludePort() throws Exception {

        Configuration c = new Configuration();

        c.setAddress("127.0.0.1:12345");

        c.initializeAndValidateAddress(false);

        assertEquals("127.0.0.1:12345", c.getAddress());
        assertEquals(InetAddress.getByAddress(new byte[]{127, 0, 0, 1}), c.getInetAddress());
        assertEquals(12345, c.getPort().intValue());
    }

    @Test
    public void initializeAndValidateAddress_DoesNotIncludePort() throws Exception {

        Configuration c = new Configuration();

        c.setAddress("127.0.0.1");

        c.initializeAndValidateAddress(false);

        assertEquals("127.0.0.1", c.getAddress());
        assertEquals(InetAddress.getByAddress(new byte[]{127, 0, 0, 1}), c.getInetAddress());
        assertNull(c.getPort());
    }

    // isVerbose() -----------------------------------------------------------------------------------------------------

    @Test
    public void notVerbose() throws Exception {

        Configuration c = new Configuration();
        assertFalse(c.isVerbose());

    }

    @Test
    public void verbose() throws Exception {

        try {

            System.setProperty(Configuration.VERBOSE_SYSTEM_PROPERTY_NAME, "true");

            Configuration c = new Configuration();

            assertTrue(c.isVerbose());

        }
        finally {

            System.clearProperty(Configuration.VERBOSE_SYSTEM_PROPERTY_NAME);
        }
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
