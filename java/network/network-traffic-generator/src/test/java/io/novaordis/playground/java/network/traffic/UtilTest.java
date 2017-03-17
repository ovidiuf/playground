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
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketAddress;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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

    // validatePort() --------------------------------------------------------------------------------------------------

    @Test
    public void validatePort_Negative() throws Exception {

        try {

            Util.validatePort(-1);
            fail("should throw exception");
        }
        catch(UserErrorException e) {

            String msg = e.getMessage();
            assertTrue(msg.contains("port out of range"));
        }
    }

    @Test
    public void validatePort_Zero() throws Exception {

        Util.validatePort(0);
    }

    @Test
    public void validatePort_Max() throws Exception {

        Util.validatePort(65535);
    }

    @Test
    public void validatePort_BeyondMax() throws Exception {

        try {

            Util.validatePort(65536);
            fail("should throw exception");
        }
        catch(UserErrorException e) {

            String msg = e.getMessage();
            assertTrue(msg.contains("port out of range"));
        }
    }

    // computeLocalEndpoint() ------------------------------------------------------------------------------------------

    @Test
    public void computeLocalEndpoint_Null() throws Exception {

        SocketAddress sa = Util.computeLocalEndpoint(null, null, null, null, null);
        //noinspection ConstantConditions
        assertNull(sa);
    }

    @Test
    public void computeLocalEndpoint_NetworkInterface() throws Exception {

        NetworkInterface ni = getLocalhostNetworkInterface();

        InetSocketAddress sa = Util.computeLocalEndpoint(ni, null, null, null, null);
        assertNotNull(sa);

        assertEquals(InetAddress.getByName("127.0.0.1"), sa.getAddress());
        assertEquals(0, sa.getPort());
    }

    @Test
    public void computeLocalEndpoint_NetworkInterface_And_LocalAddress_Same() throws Exception {

        NetworkInterface ni = getLocalhostNetworkInterface();

        InetSocketAddress sa = Util.computeLocalEndpoint(ni, InetAddress.getByName("127.0.0.1"), null, null, null);
        assertNotNull(sa);

        assertEquals(InetAddress.getByName("127.0.0.1"), sa.getAddress());
        assertEquals(0, sa.getPort());
    }

    @Test
    public void computeLocalEndpoint_NetworkInterface_And_LocalAddress_Different() throws Exception {

        NetworkInterface ni = getLocalhostNetworkInterface();

        try {

            Util.computeLocalEndpoint(ni, InetAddress.getByName("127.0.0.2"), null, null, null);
            fail("should have thrown exception");
        }
        catch(UserErrorException e) {

            String msg = e.getMessage();
            assertTrue(msg.contains("incompatible values"));
            assertTrue(msg.contains("local address"));
            assertTrue(msg.contains("network interface"));
        }
    }

    @Test
    public void computeLocalEndpoint_NoNetworkInterface_LocalAddress() throws Exception {

        InetSocketAddress sa = Util.computeLocalEndpoint(null, InetAddress.getByName("127.0.0.5"), null, null, null);
        assertNotNull(sa);

        assertEquals(InetAddress.getByName("127.0.0.5"), sa.getAddress());
        assertEquals(0, sa.getPort());
    }

    @Test
    public void computeLocalEndpoint_NoNetworkInterface_Address() throws Exception {

        InetSocketAddress sa = Util.computeLocalEndpoint(null, null, null, InetAddress.getByName("127.0.0.6"), null);
        assertNotNull(sa);

        assertEquals(InetAddress.getByName("127.0.0.6"), sa.getAddress());
        assertEquals(0, sa.getPort());
    }

    @Test
    public void computeLocalEndpoint_NoNetworkInterface_LocalAddress_And_Address_Same() throws Exception {

        InetSocketAddress sa = Util.computeLocalEndpoint(
                null,
                InetAddress.getByName("127.0.0.7"), null,
                InetAddress.getByName("127.0.0.7"), null);

        assertNotNull(sa);
        assertEquals(InetAddress.getByName("127.0.0.7"), sa.getAddress());
        assertEquals(0, sa.getPort());
    }

    @Test
    public void computeLocalEndpoint_NoNetworkInterface_LocalPort() throws Exception {

        InetSocketAddress sa = Util.computeLocalEndpoint(null, null, 12345, null, null);

        assertNotNull(sa);
        InetAddress a = sa.getAddress();
        assertTrue(a.isAnyLocalAddress());
        assertEquals(12345, sa.getPort());
    }

    @Test
    public void computeLocalEndpoint_NoNetworkInterface_LocalAddress_And_Address_NotSame() throws Exception {

        try {

            Util.computeLocalEndpoint(
                    null,
                    InetAddress.getByName("127.0.0.8"), null,
                    InetAddress.getByName("127.0.0.9"), null);
            fail("should have thrown exception");
        }
        catch(UserErrorException e) {

            String msg = e.getMessage();
            assertTrue(msg.contains("incompatible values: address"));
            assertTrue(msg.contains("local address"));
        }
    }

    // isIPv4() --------------------------------------------------------------------------------------------------------

    @Test
    public void isIPv4_Null() throws Exception {

        assertFalse(Util.isIPv4(null));
    }

    @Test
    public void isIPv4_IPv4() throws Exception {

        assertTrue(Util.isIPv4(InetAddress.getByAddress(new byte[]{127, 0, 0, 1})));
    }

    @Test
    public void isIPv4_IPv6() throws Exception {

        assertFalse(Util.isIPv4(InetAddress.getByAddress(new byte[]{127, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1})));
    }

    @Test
    public void isIPv6_Null() throws Exception {

        assertFalse(Util.isIPv6(null));
    }

    @Test
    public void isIPv6_IPv4() throws Exception {

        assertFalse(Util.isIPv6(InetAddress.getByAddress(new byte[]{127, 0, 0, 1})));
    }

    @Test
    public void isIPv6_IPv6() throws Exception {

        assertTrue(Util.isIPv6(InetAddress.getByAddress(new byte[]{127, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1})));
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    private static NetworkInterface getLocalhostNetworkInterface() throws Exception {

        NetworkInterface ni = NetworkInterface.getByInetAddress(InetAddress.getByName("127.0.0.1"));

        if (ni == null) {

            throw new IllegalArgumentException("null localhost network interface");
        }

        return ni;
    }



    // Inner classes ---------------------------------------------------------------------------------------------------

}
