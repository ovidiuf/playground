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

import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketAddress;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 3/14/17
 */
public abstract class BaseAction implements Action {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private InetSocketAddress multicastAddress;
    private NetworkInterface networkInterface;

    // Constructors ----------------------------------------------------------------------------------------------------

    protected BaseAction(String[] args) throws Exception {

//        String ni = null, mcaap = null;
//
//        //noinspection ForLoopReplaceableByForEach
//        for(int i = 0; i < args.length; i ++) {
//
//            if (ni == null) {
//
//                ni = args[i];
//            }
//            else if (mcaap == null) {
//
//                mcaap = args[i];
//            }
//
//            //
//            // ignore the rest
//            //
//        }
//
//        if (ni == null) {
//
//            throw new UserErrorException("must specify a network interface");
//        }
//
//        networkInterface = NetworkInterface.getByName(ni);
//
//        if (networkInterface == null) {
//
//            throw new UserErrorException("no such network interface " + ni);
//        }
//
//        if (mcaap == null) {
//
//            throw new UserErrorException("must specify a multicast-address:port");
//        }
//
//        multicastAddress = Util.colonSeparatedStringToSocketAddress(mcaap);
    }

    // Public ----------------------------------------------------------------------------------------------------------

    public InetSocketAddress getMulticastAddress() {

        return multicastAddress;
    }

    public NetworkInterface getNetworkInterface() {

        return networkInterface;
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
