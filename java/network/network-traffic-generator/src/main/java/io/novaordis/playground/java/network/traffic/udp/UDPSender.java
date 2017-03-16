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
import io.novaordis.playground.java.network.traffic.Sender;
import io.novaordis.playground.java.network.traffic.Util;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 3/16/17
 */
public class UDPSender implements Sender {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private Configuration c;

    // Constructors ----------------------------------------------------------------------------------------------------

    public UDPSender(Configuration c) {

        this.c = c;
    }

    // Sender implementation -------------------------------------------------------------------------------------------

    @Override
    public void send() throws Exception {

        //
        // we only need to specify the remote address and port - we don't need to specify anything related to the
        // local interface to bind to, the JVM networking code and the kernel routing will take care of that; however
        // we can force a specific interface to be used, if we want to
        //

        DatagramSocket s = new DatagramSocket();

        NetworkInterface ni = c.getNetworkInterface();

        if (ni != null) {




        }

        String payload = "test";

        Integer remotePort = c.getPort();
        InetAddress remoteAddress = c.getInetAddress();
        DatagramPacket p = new DatagramPacket(payload.getBytes(), payload.length(), remoteAddress, remotePort);

        Util.dumpState(c, s);

        s.send(p);

        System.out.println(payload.length() +
                " bytes have been sent via " + s.getLocalAddress() + ":" + s.getLocalPort() + " to " +
                remoteAddress + ":" + remotePort);


        s.close();
    }

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
