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

package io.novaordis.playground.java.network.traffic.udp;

import io.novaordis.playground.java.network.traffic.AddressType;
import io.novaordis.playground.java.network.traffic.Configuration;
import io.novaordis.playground.java.network.traffic.Receiver;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 3/16/17
 */
public class UDPReceiver implements Receiver {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private Configuration c;

    // Constructors ----------------------------------------------------------------------------------------------------

    public UDPReceiver(Configuration c) {

        this.c = c;
    }

    // Receiver implementation -----------------------------------------------------------------------------------------

    @Override
    public void receive() throws Exception {


        Integer localPort = c.getPort();
        InetAddress localAddress = c.getNetworkInterfaceAddress(AddressType.IPv4);
        SocketAddress socketAddress = new InetSocketAddress(localAddress, localPort);

        DatagramSocket receivingSocket = new DatagramSocket(socketAddress);

        byte[] buffer = new byte[1024];
        DatagramPacket datagram = new DatagramPacket(buffer, buffer.length);

        System.out.println(
                "listening for UDP traffic on " +
                receivingSocket.getLocalAddress() + ":" + receivingSocket.getLocalPort());

        //noinspection InfiniteLoopStatement
        while(true) {

            receivingSocket.receive(datagram);
            report(datagram);
        }
    }

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    private void report(DatagramPacket datagram) {

        System.out.println(datagram.getLength() + " bytes from " + datagram.getAddress() + ":" + datagram.getPort());
    }

    // Inner classes ---------------------------------------------------------------------------------------------------

}
