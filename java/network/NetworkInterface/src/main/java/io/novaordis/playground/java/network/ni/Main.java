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

package io.novaordis.playground.java.network.ni;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 3/15/17
 */
public class Main {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    public static void main(String[] args) throws Exception {


        Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();

        while(e.hasMoreElements()) {

            NetworkInterface ni = e.nextElement();

            String s = networkInterfaceToString(ni);

            System.out.println(s);
        }
    }

    public static String networkInterfaceToString(NetworkInterface ni) throws Exception {

        String s = "";


        int index = ni.getIndex();
        String name = ni.getName();
        String displayName = ni.getDisplayName();

        s += index + ". " + name;

        if (!name.equals(displayName)) {

            s += " (" + displayName + ")";
        }

        s += " <";
        AtomicBoolean first = new AtomicBoolean(true);



        boolean loopback = ni.isLoopback();
        if (loopback) {

            s += handleFirst(first);
            s += "LOOPBACK";
        }



        boolean up = ni.isUp();
        if (up) {

            s += handleFirst(first);
            s += "UP";
        }



        boolean multicast = ni.supportsMulticast();
        if (multicast) {

            s += handleFirst(first);
            s += "MULTICAST";
        }



        boolean pointToPoint =  ni.isPointToPoint();
        if (pointToPoint) {

            s += handleFirst(first);
            s += "POINT-TO-POINT";
        }


        boolean virtual = ni.isVirtual();
        if (virtual) {

            s += handleFirst(first);
            s += "VIRTUAL";
        }

        s += ">";



        int mtu = ni.getMTU();
        s += " mtu " + mtu;


        NetworkInterface parent = ni.getParent();
        if (parent != null) {

            s += "\n        parent: " + parent;
        }


        Enumeration<NetworkInterface> subinterfaces = ni.getSubInterfaces();
        if (subinterfaces.hasMoreElements()) {

            s += "\n        sub-interfaces: " + parent;
        }



        byte[] hardwareAddress = ni.getHardwareAddress();
        s += "\n        hardware address:    " + byteArrayToHardwareAddress(hardwareAddress);


        List<InterfaceAddress> interfaceAddresses = ni.getInterfaceAddresses();
        s += "\n        interface addresses: " + listOfInterfaceAddresses(interfaceAddresses);


        Set<InetAddress> inetAddressesObtainedFromInterfaceAddresses = new HashSet<>();
        for(InterfaceAddress a: interfaceAddresses) {
            inetAddressesObtainedFromInterfaceAddresses.add(a.getAddress());
        }

        Set<InetAddress> inetAddresses = new HashSet<>();
        for(Enumeration<InetAddress> e = ni.getInetAddresses(); e.hasMoreElements(); ) {
            inetAddresses.add(e.nextElement());
        }

        if (!identical(inetAddressesObtainedFromInterfaceAddresses, inetAddresses)) {

            s += "\n        THE LIST OF InetAddresses obtained directly differs from the list of InetAddresses obtained from interface addresses";
        }

        return s;
    }

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    private static String handleFirst(AtomicBoolean first) {

        if (first.get()) {

            first.set(false);
            return "";
        }
        else {

            return ", ";
        }
    }

    private static String byteArrayToHardwareAddress(byte[] hardwareAddress) {

        if (hardwareAddress == null) {

            return "N/A";
        }

        String s = "";

        for(int i = 0; i < hardwareAddress.length; i ++) {

            byte b = hardwareAddress[i];

            int firstHalf = 0x00F0 & b;
            firstHalf = firstHalf >>> 4;
            int secondHalf = 0x000F & b;

            s += Integer.toHexString(firstHalf) + Integer.toHexString(secondHalf);

            if (i < hardwareAddress.length - 1) {

                s += ":";
            }

        }

        return s;
    }

    private static String listOfInterfaceAddresses(List<InterfaceAddress> interfaceAddresses) {

        if (interfaceAddresses == null || interfaceAddresses.isEmpty()) {

            return "N/A";
        }

        String s = "";

        for(Iterator<InterfaceAddress> i = interfaceAddresses.iterator(); i.hasNext(); ) {

            InterfaceAddress ia = i.next();

            InetAddress a = ia.getAddress();
            short networkPrefix = ia.getNetworkPrefixLength();
            s += a.getHostAddress() + "/" + networkPrefix;

            if (i.hasNext()) {

                s += ", ";
            }
        }

        return s;
    }

    private static boolean identical(Set<InetAddress> set1, Set<InetAddress> set2) {

        if (set1.size() != set2.size()) {

            return false;
        }

        for(InetAddress i: set1) {

            if (!set2.contains(i)) {

                return false;
            }
        }

        return true;
    }


    // Inner classes ---------------------------------------------------------------------------------------------------

}
