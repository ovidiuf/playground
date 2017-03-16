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

import java.net.InetAddress;
import java.net.NetworkInterface;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 3/14/17
 */
public abstract class BaseAction implements Action {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private NetworkInterface networkInterface;

    private int port;
    private InetAddress multicastAddress;

    // Constructors ----------------------------------------------------------------------------------------------------

    protected BaseAction(String[] args) throws Exception {

        String ni = null, mcaap = null;

        //noinspection ForLoopReplaceableByForEach
        for(int i = 0; i < args.length; i ++) {

            if (ni == null) {

                ni = args[i];
            }
            else if (mcaap == null) {

                mcaap = args[i];
            }

            //
            // ignore the rest
            //
        }

        if (ni == null) {

            throw new UserErrorException("must specify a network interface name");
        }

        networkInterface = NetworkInterface.getByName(ni);

        if (networkInterface == null) {

            throw new UserErrorException("no such network interface " + ni);
        }

        if (!networkInterface.supportsMulticast()) {

            throw new UserErrorException("the " + ni + " network interface does not support multicast");
        }

        if (mcaap == null) {

            throw new UserErrorException("must specify a multicast-address:port");
        }

        int i = mcaap.indexOf(':');

        if (i == -1) {

            throw new UserErrorException(
                    "the multicast address and port must be specified in addr:port format, but we got \"" + mcaap + "\"");
        }

        String addr = mcaap.substring(0, i);
        multicastAddress = InetAddress.getByName(addr);

        if (!multicastAddress.isMulticastAddress()) {

            throw new UserErrorException(addr + " is not a multicast address");
        }

        String p = mcaap.substring(i + 1);

        try {

            port = Integer.parseInt(p);
        }
        catch(Exception e) {

            throw new UserErrorException("invalid port " + p);
        }
    }

    // Public ----------------------------------------------------------------------------------------------------------


    public NetworkInterface getNetworkInterface() {

        return networkInterface;
    }

    /**
     * Verified a multicast address.
     */
    public InetAddress getMulticastAddress() {

        return multicastAddress;
    }

    public int getPort() {

        return port;
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
