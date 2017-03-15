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

package io.novaordis.playground.java.multicast;

import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.InterfaceAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.util.List;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 3/14/17
 */
public class Send extends BaseAction implements Action {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    public Send(String[] args) throws Exception {

        super(args);

    }

    // Action implementation -------------------------------------------------------------------------------------------

    @Override
    public void execute() throws Exception {

//        InetSocketAddress multicastAddress = getMulticastAddress();
//
//        String s = "test";
//        byte[] buffer = s.getBytes();
//
//        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, multicastAddress);
//
//        NetworkInterface networkInterface = getNetworkInterface();
//        List<InterfaceAddress> networkInterfaceAddresses = networkInterface.getInterfaceAddresses();
//        InterfaceAddress a = networkInterfaceAddresses.get(1);
//
//        DatagramSocket socket = new DatagramSocket(multicastAddress.getPort(), a.getAddress());
//
//        socket.send(packet);
//
//        System.out.println("multicast packet sent to " + multicastAddress + " via " + socket.getInetAddress());


        int port = 10200;

        MulticastSocket s = new MulticastSocket();

        InetSocketAddress group = new InetSocketAddress("228.5.6.7", port);

        NetworkInterface ni = NetworkInterface.getByName("en0");

        s.joinGroup(group, ni);

        String t = "test";
        byte[] buffer = t.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group.getAddress(), port);

        s.send(packet);
    }

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
