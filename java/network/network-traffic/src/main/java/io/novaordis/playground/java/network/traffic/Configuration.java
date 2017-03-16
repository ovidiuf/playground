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
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 3/15/17
 */
public class Configuration {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private Mode mode;
    private Protocol protocol;

    private String address;
    private InetAddress inetAddress;

    private String interf;
    private NetworkInterface networkInterface;

    private Integer port;


    // Constructors ----------------------------------------------------------------------------------------------------

    public Configuration(String[] args) throws UserErrorException {

        //
        // first argument is always the mode
        //

        if (args == null || args.length == 0) {

            throw new UserErrorException("missing mode");
        }

        for(Mode m: Mode.values()) {

            if (m.toString().equalsIgnoreCase(args[0])) {

                mode = m;
                break;
            }
        }

        if (mode == null) {

            throw new UserErrorException("unknown mode " + args[0]);
        }

        for(int i = 1; i < args.length; i ++) {

            String arg = args[i];

            if (arg.startsWith("--protocol=")) {

                arg = arg.substring("--protocol=".length());
                this.protocol = Protocol.fromString(arg);

            }
            else if (arg.startsWith("--address=")) {

                address = arg.substring("--address=".length());

            }
            else if (arg.startsWith("--interface=")) {

                setInterface(arg.substring("--interface=".length()));

            }
            else if (arg.startsWith("--port=")) {

                arg = arg.substring("--port=".length());

                try {

                    port = Integer.parseInt(arg);
                }
                catch(Exception e) {

                    throw new UserErrorException("invalid port value " + arg + ", not an integer");
                }

                if (port <= 0 || port >= 65536) {

                    throw new UserErrorException("invalid port value " + arg + ", out of range");
                }
            }
            else {

                throw new UserErrorException("unknown argument " + arg);
            }

        }
    }

    // Public ----------------------------------------------------------------------------------------------------------

    /**
     * Validates and resolves various values, such as the network interface, etc.
     */
    public void validate() throws UserErrorException {

        validateProtocol();
        initializeAndValidateNetworkInterface();
        initializeAndValidateAddress();

    }

    public Mode getMode() {

        return mode;
    }

    public Protocol getProtocol() {

        return protocol;
    }

    /**
     * @return the value as passed with --address= command line argument. Null means the argument is not present in the
     * command line. It represents the remote address to send to. In case of a receive situation, it must be null.
     */
    public String getAddress() {

        return address;
    }

    /**
     * The remote address to send to. In case of a receive situation, it is null.
     */
    public InetAddress getInetAddress() {

        //
        // this value has to be "resolved" from the string configuration
        //

        return inetAddress;
    }

    /**
     * @return the value as passed with --port= command line argument. Null means the argument is not present in
     * the command line. Depending on the mode, it represents the local port to bind to (for receiving) or the remote
     * port to send to (for sending).
     */
    public Integer getPort() {

        return port;
    }


    /**
     * @return the value as passed with --interface= command line argument. Null means the argument is not present in
     * the command line.
     */
    public String getInterface() {

        return interf;
    }


    //
    // these values have to be "resolved" from the string configuration
    //

    /**
     * May return null if no --interface was specified.
     */
    public NetworkInterface getNetworkInterface() {

        return networkInterface;
    }

    public String getInterfaceName() {

        if (networkInterface == null) {

            return null;
        }

        return networkInterface.getName();
    }

    public List<InterfaceAddress> getNetworkInterfaceAddresses() {

        if (networkInterface == null) {

            return Collections.emptyList();
        }

        return networkInterface.getInterfaceAddresses();
    }

    /**
     * @return may return null if there is no matching address
     */
    public InetAddress getNetworkInterfaceAddress(AddressType type) {

        List<InterfaceAddress> interfaceAddresses = getNetworkInterfaceAddresses();

        InetAddress a = null;

        for(InterfaceAddress ia: interfaceAddresses) {

            InetAddress iaa = ia.getAddress();
            if (type.match(iaa)) {

                if (a != null) {

                    throw new IllegalArgumentException(
                            "more than one InetAddress matches " + type + " for " + getNetworkInterface());
                }

                a = iaa;
            }
        }

        return a;
    }

    @Override
    public String toString() {

        String s = "configuration:\n";
        s += "     mode                       " + mode + "\n";
        s += "     interface                  " + interf + "\n";
        s += "     network interface          " + networkInterface + "\n";
        s += "     network interface name     " + getInterfaceName() + "\n";
        s += "     network interface addresses" + getNetworkInterfaceAddresses() + "\n";
        s += "     protocol                   " + protocol + "\n";
        s += "     address                    " + address + "\n";
        s += "     internet address           " +
                (inetAddress == null ? "null" : inetAddress.getClass().getSimpleName() + " " + inetAddress) + "\n";
        s += "     port                       " + port + "\n";

        return s;
    }

    // Package protected -----------------------------------------------------------------------------------------------

    void setInterface(String s) {

        this.interf = s;
    }

    void initializeAndValidateNetworkInterface() throws UserErrorException {

        String i = getInterface();

        if (i == null) {

            return;
        }

        try {

            networkInterface = NetworkInterface.getByName(i);
        }
        catch(Exception e) {

            throw new UserErrorException("failed to resolve network interface " + i, e);
        }

        if (networkInterface != null) {

            //
            // return, we're good
            //
            return;
        }

        //
        // interpret the string as an address/host name and return the matching interface if any
        //

        try {

            outer:
            for (Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces(); e.hasMoreElements(); ) {

                NetworkInterface ni = e.nextElement();

                for(Enumeration<InetAddress> ae = ni.getInetAddresses(); ae.hasMoreElements(); ) {

                    InetAddress a = ae.nextElement();

                    byte[] b = Util.addressToBytes(i);

                    if (Util.identical(b, a.getAddress())) {

                        //
                        // found it
                        //
                        networkInterface = ni;
                        break outer;
                    }
                }
            }
        }
        catch(Exception e) {

            throw new UserErrorException("NetworkInterface.getNetworkInterfaces() call failed", e);
        }

        if (networkInterface != null) {

            //
            // return, we're good
            //
            return;
        }

        throw new UserErrorException("no such interface: " + i);
    }

    /**
     * Turns the address string into an InetAddress.
     */
    void initializeAndValidateAddress() throws UserErrorException {

        String i = getAddress();

        if (i == null) {

            return;
        }

        try {

            inetAddress = InetAddress.getByName(i);
        }
        catch(Exception e) {

            throw new UserErrorException("invalid address " + i, e);
        }
    }

    void validateProtocol() throws UserErrorException {

        if (getProtocol() == null) {

            throw new UserErrorException("a protocol must be specified with --protocol=udp|multicast|tcp|...");
        }


    }

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
