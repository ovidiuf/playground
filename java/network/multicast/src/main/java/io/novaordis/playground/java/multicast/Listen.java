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

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketAddress;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 3/14/17
 */
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

//        SocketAddress multicastAddress = getMulticastAddress();
//        NetworkInterface networkInterface = getNetworkInterface();
//
//        System.out.println("");
//        System.out.println("listening for " + multicastAddress + " on " + networkInterface);
//
//        serverSocket.joinGroup(multicastAddress, networkInterface);
//
//
//        byte[] buffer = new byte[1024];
//
//        //noinspection InfiniteLoopStatement
//        while(true) {
//
//            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
//            serverSocket.receive(packet);
//
//            int i = packet.getLength();
//
//            System.out.println("received " + i + " bytes");
//        }


        int port = 10200;
        this.serverSocket = new MulticastSocket();

        NetworkInterface ni = NetworkInterface.getByName("en0");

        ni.getInterfaceAddresses();

        InetSocketAddress group = new InetSocketAddress("228.5.6.7", port);

        serverSocket.joinGroup(group, ni);

        byte[] buffer = new byte[1024];

        //noinspection InfiniteLoopStatement
        while(true) {

            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            serverSocket.receive(packet);

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
