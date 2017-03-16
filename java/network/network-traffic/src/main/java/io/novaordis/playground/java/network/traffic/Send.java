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
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.InterfaceAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;

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

        int port = getPort();
        InetAddress multicastAddress = getMulticastAddress();
        MulticastSocket s = new MulticastSocket();
        s.setLoopbackMode(false);
        s.setNetworkInterface(getNetworkInterface());

        String t = "test";
        byte[] buffer = t.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, multicastAddress, port);
        s.setTimeToLive(10);
        s.send(packet);

        System.out.println("multicast packet sent to " + multicastAddress + ":" + port + " using " + s.getNetworkInterface());
        s.close();
    }

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
