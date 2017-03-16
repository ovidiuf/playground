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
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.InterfaceAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 3/14/17
 */
@Deprecated
public class Listen extends BaseAction implements Action {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private MulticastSocket serverSocket;

    // Constructors ----------------------------------------------------------------------------------------------------

    public Listen(String[] args) throws Exception {

        super(args);
    }

    // Action implementation -------------------------------------------------------------------------------------------

    @Override
    public void execute() throws Exception {

        NetworkInterface ni = getNetworkInterface();

        InterfaceAddress interfaceAddress = null;

        for(InterfaceAddress ia: ni.getInterfaceAddresses()) {

            InetAddress inetAddress = ia.getAddress();

            //
            // stick with IPv4 addresses for the time being
            //
            if (!(inetAddress instanceof Inet4Address)) {

                continue;
            }

            if (interfaceAddress != null) {

                throw new UserErrorException("more than IPv4 address associated with " + ni);
            }

            interfaceAddress = ia;
        }

        if (interfaceAddress == null) {

            throw new UserErrorException("no IPv4 address associated with " + ni);
        }

        int port = getPort();
        InetAddress multicastAddress = getMulticastAddress();

        InetSocketAddress interfaceSocketAddress = new InetSocketAddress(interfaceAddress.getAddress(), port);

        serverSocket = new MulticastSocket();
        serverSocket.setNetworkInterface(ni);

        System.out.println(serverSocket.getInterface() + " bound: " + serverSocket.isBound());

        serverSocket.joinGroup(multicastAddress);

        System.out.println(serverSocket.getInterface() + " joined multicast address " + multicastAddress);

        byte[] buffer = new byte[4];

        //noinspection InfiniteLoopStatement
        while(true) {

            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            this.serverSocket.receive(packet);

            int i = packet.getLength();

            System.out.println("received " + i + " bytes");
        }
    }

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
