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

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 3/14/17
 */
public class Util {

    // Constants -------------------------------------------------------------------------------------------------------

    public static final Pattern IPv4_FORMAT = Pattern.compile("([0-9]+)\\.([0-9]+)\\.([0-9]+)\\.([0-9]+)");

    // Static ----------------------------------------------------------------------------------------------------------

    /**
     * Attempts to interpret the address as a IPv4 or IPv6 byte[] address.
     *
     * @return the byte[4] or byte[16] or null if the conversion cannot be performed.
     *
     * @throws UserErrorException
     */
    public static byte[] addressToBytes(String address) throws UserErrorException {

        Matcher m = IPv4_FORMAT.matcher(address);

        if (m.find()) {

            byte[] result = new byte[4];

            String b = m.group(1);
            result[0] = (byte)Integer.parseInt(b);
            b = m.group(2);
            result[1] = (byte)Integer.parseInt(b);
            b = m.group(3);
            result[2] = (byte)Integer.parseInt(b);
            b = m.group(4);
            result[3] = (byte)Integer.parseInt(b);

            return result;
        }

        return null;
    }

    public static boolean identical(byte[] b, byte[] b2) {

        if (b == null || b2 == null) {

            return false;
        }

        if (b.length != b2.length) {

            return false;
        }

        for(int i = 0; i < b.length; i ++) {

            if (b[i] != b2[i]) {

                return false;
            }
        }

        return true;
    }

    public static void validatePort(int port) throws UserErrorException {

        if (port < 0 || port > 0xFFFF) {

            throw new UserErrorException("port out of range: " + port);
        }
    }

    /**
     * Heuristics to come up with a local endpoint coordinates, based on the values provided as follows:
     *
     * @param ni the NetworkInterface (inferred based on the value of --interface=). May be null.
     * @param localAddress the local address (inferred based on the value of --local-address=). May be null.
     * @param localPort the local port (inferred based on the value of --local-port=). May be null.
     * @param address the address (inferred based on the value of --address=). May have a "local address" semantics.
     *                May be null.
     * @param port the port (inferred based on the value of --ports=). May have a "local port" semantics.
     *                May be null.
     *
     * @return may return null
     *
     * @exception UserErrorException on incompatible values.
     */
    public static InetSocketAddress computeLocalEndpoint(
            NetworkInterface ni, InetAddress localAddress, Integer localPort, InetAddress address, Integer port)
            throws UserErrorException {

        InetAddress effectiveAddress = null;

        if (ni != null) {

            for(Enumeration<InetAddress> e = ni.getInetAddresses(); e.hasMoreElements(); ) {

                InetAddress a = e.nextElement();

                if (effectiveAddress == null) {

                    effectiveAddress = a;
                }
                else if (Util.isIPv6(effectiveAddress) && Util.isIPv4(a)) {

                    //
                    // prefer IPv4 addresses
                    //

                    effectiveAddress = a;
                }
            }

            //
            // check conflict if both network interface and local address are specified
            //

            if (localAddress != null) {

                if (!localAddress.equals(effectiveAddress)) {

                    throw new UserErrorException(
                            "incompatible values: network interface " + effectiveAddress + "  and local address " +
                                    localAddress);
                }
            }
        }
        else {

            //
            // no network interface specified, use the local address or the address, and check incompatibility if both
            // are specified
            //

            if (localAddress != null && address == null) {

                effectiveAddress = localAddress;
            }
            else if (localAddress == null && address != null) {

                effectiveAddress = address;
            }
            else if (localAddress != null) {

                //
                // make sure they don't conflict
                //
                if (!localAddress.equals(address)) {

                    throw new UserErrorException(
                            "incompatible values: address " + address + " and local address " +
                                    localAddress);

                }

                effectiveAddress = localAddress;
            }
            else {

                //
                // both are null, no network interface specified, effective address remains null
                //
                effectiveAddress = null;
            }
        }

        Integer effectivePort;

        if (localPort != null && port == null) {

            effectivePort = localPort;
        }
        else if (localPort == null && port != null) {

            effectivePort = port;
        }
        else if (localPort != null && !localPort.equals(port)) {

            throw new UserErrorException("incompatible values: local port " + localPort + " and port " + port);
        }
        else {

            effectivePort = localPort;
        }

        if (effectiveAddress == null) {

            if (effectivePort == null) {

                return null;
            }

            return new InetSocketAddress(effectivePort);
        }

        if (effectivePort == null) {

            effectivePort = 0;
        }

        validatePort(effectivePort);

        return new InetSocketAddress(effectiveAddress, effectivePort);
    }

    public static boolean isIPv4(InetAddress a) {

        return (a instanceof Inet4Address);

    }

    public static boolean isIPv6(InetAddress a) {

        return (a instanceof Inet6Address);

    }

    //
    // display functions -----------------------------------------------------------------------------------------------
    //

    public static String inetAddressToString(InetAddress a) {

        if (a == null) {

            return "null";
        }

        String s = a.getClass().getSimpleName();
        s += " ";
        s += a;

        if (a.isMulticastAddress()) {

            s += " (MULTICAST)";
        }

        return s;
    }

    public static void displayInfo() throws Exception {

        for(Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces(); e.hasMoreElements(); ) {

            NetworkInterface ni = e.nextElement();

            System.out.println(ni.getName());
        }
    }

    public static void dumpState(Configuration c, DatagramSocket s, DatagramPacket p) throws Exception {

        if (!c.isVerbose()) {
            return;
        }

        System.out.println();
        System.out.println(c);

        String d = "socket:\n\n";

        d += "    type:                        " + s.getClass().getSimpleName() + "\n";
        d += "    is bound:                    " + s.isBound() + "\n";
        d += "    is connected:                " + s.isConnected() + "\n";
        d += "    local socket address:        " + s.getLocalSocketAddress() + "\n";
        d += "    local address:               " + s.getLocalAddress() + "\n";
        d += "    local port:                  " + s.getLocalPort() + "\n";

        if (p != null) {

            d = "\npacket:\n\n";

            d += "    target address:              " + p.getAddress() + "\n";
            d += "    target port:                 " + p.getPort() + "\n";
        }

        System.out.println(d);
    }

    public static String truncate(byte[] payload, int length) {

        if (payload.length <= length) {

            return new String(payload);
        }

        return new String(payload, 0, length) + "[...]";
    }

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    private Util() {
    }

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
