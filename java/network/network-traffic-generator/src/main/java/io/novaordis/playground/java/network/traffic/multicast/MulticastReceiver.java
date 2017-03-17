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

package io.novaordis.playground.java.network.traffic.multicast;

import io.novaordis.playground.java.network.traffic.Configuration;
import io.novaordis.playground.java.network.traffic.UserErrorException;
import io.novaordis.playground.java.network.traffic.Util;
import io.novaordis.playground.java.network.traffic.udp.UDPReceiver;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 3/16/17
 */
public class MulticastReceiver extends UDPReceiver {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private InetAddress multicastGroup;

    // Constructors ----------------------------------------------------------------------------------------------------

    public MulticastReceiver(Configuration c) {
        super(c);
    }

    // UDPReceiver overrides -------------------------------------------------------------------------------------------

    @Override
    public void start() throws Exception {

        Configuration configuration = getConfiguration();

        InetSocketAddress receivingSocketAddress = Util.
                computeLocalEndpoint(
                        configuration.getNetworkInterface(),
                        configuration.getLocalInetAddress(),
                        configuration.getLocalPort(),
                        null, // the local address is interpreted as multicast group to join
                        configuration.getPort());

        MulticastSocket s = new MulticastSocket(receivingSocketAddress);

        //
        // join the multicast group
        //

        multicastGroup = configuration.getInetAddress();

        if (multicastGroup == null) {

            throw new UserErrorException("missing required multicast address, use --address= to specify");
        }

        if (!multicastGroup.isMulticastAddress()) {

            throw new UserErrorException(multicastGroup + " not a multicast address");
        }

        s.joinGroup(multicastGroup);

        setSocket(s);
    }

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    @Override
    protected void reportWeStartedToListen() {

        Configuration c = getConfiguration();
        DatagramSocket s = getSocket();

        System.out.println(
                "listening for " + c.getProtocol().toString().toUpperCase() + " traffic on " +
                        s.getLocalAddress() + ":" + s.getLocalPort() + " for multicast group " + multicastGroup);

    }

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
