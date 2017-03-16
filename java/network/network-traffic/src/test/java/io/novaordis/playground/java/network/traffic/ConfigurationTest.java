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
    public void constructor_portOutOfRange() throws Exception {

        String[] args = {

                "receive",
                "--protocol=multicast",
                "--interface=eth1",
                "--port=99999"
        };

        try {

            new Configuration(args);
            fail("should throw exception");
        }
        catch(UserErrorException e) {

            String msg = e.getMessage();
            assertTrue(msg.contains("invalid port value"));
            assertTrue(msg.contains("out of range"));
        }
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
