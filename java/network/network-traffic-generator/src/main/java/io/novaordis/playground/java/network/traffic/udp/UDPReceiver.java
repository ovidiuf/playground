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

import io.novaordis.playground.java.network.traffic.Configuration;
import io.novaordis.playground.java.network.traffic.Receiver;
import io.novaordis.playground.java.network.traffic.Util;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 3/16/17
 */
public class UDPReceiver implements Receiver {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private Configuration configuration;

    // Constructors ----------------------------------------------------------------------------------------------------

    public UDPReceiver(Configuration c) {

        this.configuration = c;
    }

    // Receiver implementation -----------------------------------------------------------------------------------------

    @Override
    public void receive() throws Exception {

        InetSocketAddress receivingSocketAddress = Util.computeLocalEndpoint(
                configuration.getNetworkInterface(),
                configuration.getLocalInetAddress(),
                configuration.getLocalPort(),
                configuration.getInetAddress(),
                configuration.getPort());

        DatagramSocket receivingSocket = new DatagramSocket(receivingSocketAddress);

        byte[] buffer = new byte[1024];
        DatagramPacket datagram = new DatagramPacket(buffer, buffer.length);

        Util.dumpState(configuration, receivingSocket);

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

        byte[] buffer = datagram.getData();
        int length = datagram.getLength();
        byte[] payload = new byte[length];
        System.arraycopy(buffer, 0, payload, 0, length);

        System.out.println(datagram.getAddress() + ":" + datagram.getPort() + " " + length + " byte(s): " +
                Util.truncate(payload, 10));
    }

    // Inner classes ---------------------------------------------------------------------------------------------------

}
