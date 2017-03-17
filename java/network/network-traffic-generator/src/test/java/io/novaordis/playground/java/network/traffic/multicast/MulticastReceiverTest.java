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

package io.novaordis.playground.java.network.traffic.multicast;

import io.novaordis.playground.java.network.traffic.Configuration;
import io.novaordis.playground.java.network.traffic.Mode;
import io.novaordis.playground.java.network.traffic.Protocol;
import io.novaordis.playground.java.network.traffic.UserErrorException;
import io.novaordis.playground.java.network.traffic.udp.UDPReceiverTest;
import org.junit.Test;

import java.net.NetworkInterface;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 3/16/17
 */
public class MulticastReceiverTest extends UDPReceiverTest {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    // Tests -----------------------------------------------------------------------------------------------------------

    @Test
    public void start_MulticastAddressNotProvided() throws Exception {

        Configuration c = new Configuration();
        assertNull(c.getInetAddress());

        MulticastReceiver r = getReceiverToTest(c);

        try {

            r.start();
            fail("should have thrown exception");
        }
        catch(UserErrorException e) {

            String msg = e.getMessage();
            assertTrue(msg.contains("missing required multicast address"));
        }
    }

    @Test
    public void start_MulticastAddressIsNotAMulticastAddress() throws Exception {

        Configuration c = new Configuration();
        c.setProtocol(Protocol.multicast);
        c.setMode(Mode.receive);
        c.setAddress("127.0.0.1");
        c.setPort(7777);
        c.validate();


        MulticastReceiver r = getReceiverToTest(c);

        try {

            r.start();
            fail("should have thrown exception");
        }
        catch(UserErrorException e) {

            String msg = e.getMessage();
            assertTrue(msg.contains("not a multicast address"));
        }
    }

    // Package protected -----------------------------------------------------------------------------------------------

    @Override
    protected MulticastReceiver getReceiverToTest(Configuration c) throws Exception {

        return new MulticastReceiver(c);
    }

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
