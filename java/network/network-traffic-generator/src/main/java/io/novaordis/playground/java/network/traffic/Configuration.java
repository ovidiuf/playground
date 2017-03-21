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
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 3/15/17
 */
public class Configuration {

    // Constants -------------------------------------------------------------------------------------------------------

    public static final String VERBOSE_SYSTEM_PROPERTY_NAME = "verbose";

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private boolean verbose;

    private Mode mode;
    private Protocol protocol;

    private String address;
    private InetAddress inetAddress;

    private String interf;
    private NetworkInterface networkInterface;

    private Integer port;

    private String localAddress;
    private InetAddress localInetAddress;
    private Integer localPort;

    private String payload;

    // Constructors ----------------------------------------------------------------------------------------------------

    public Configuration(String[] args) throws UserErrorException {

        this();

        //
        // first argument is always the mode
        //

        if (args == null || args.length == 0) {

            throw new UserErrorException("missing mode, use one of " + Arrays.asList(Mode.values()));
        }

        for(Mode m: Mode.values()) {

            if (m.toString().equalsIgnoreCase(args[0])) {

                setMode(m);
                break;
            }
        }

        if (mode == null) {

            throw new UserErrorException("unknown mode " + args[0]);
        }

        payload = null;

        for(int i = 1; i < args.length; i ++) {

            String arg = args[i];

            if (arg.startsWith("--protocol=")) {

                arg = arg.substring("--protocol=".length());
                setProtocol(Protocol.fromString(arg));

            }
            else if (arg.startsWith("--interface=")) {

                setInterface(arg.substring("--interface=".length()));

            }
            else if (arg.startsWith("--address=")) {

                setAddress(arg.substring("--address=".length()));
            }
            else if (arg.startsWith("--port=")) {

                arg = arg.substring("--port=".length());

                try {

                    setPort(Integer.parseInt(arg));
                }
                catch(Exception e) {

                    throw new UserErrorException("invalid port value " + arg + ", not an integer");
                }
            }
            else if (arg.startsWith("--local-address=")) {

                setLocalAddress(arg.substring("--local-address=".length()));
            }
            else if (arg.startsWith("--local-port=")) {

                arg = arg.substring("--local-port=".length());

                try {

                    setLocalPort(Integer.parseInt(arg));
                }
                catch(Exception e) {

                    throw new UserErrorException("invalid local port value " + arg + ", not an integer");
                }
            }
            else if (arg.startsWith("-")) {

                throw new UserErrorException("unknown option " + arg);
            }
            else {

                //
                // accumulate as payload
                //
                payload = payload == null ? arg : payload + " " + arg;
            }

        }
    }

    /**
     * Testing only.
     */
    public Configuration() {

        this.verbose = Boolean.getBoolean(VERBOSE_SYSTEM_PROPERTY_NAME);
    }

    // Public ----------------------------------------------------------------------------------------------------------

    public boolean isVerbose() {

        return verbose;
    }

    /**
     * Validates and resolves various values, such as the network interface, etc.
     */
    public void validate() throws UserErrorException {

        validateProtocol();

        if (mode.isReceive()) {

            //
            // receive
            //

            initializeAndValidateNetworkInterface(false);

            //
            // the --address may be interpreted as multicast group address
            //
            initializeAndValidateAddress(false);
            validatePort();
        }
        else if (mode.isSend()) {

            //
            // send
            //

            initializeAndValidateNetworkInterface(false);
            initializeAndValidateAddress(true);
        }

        initializeAndValidateLocalAddress();


    }

    public Mode getMode() {

        return mode;
    }

    public Protocol getProtocol() {

        return protocol;
    }

    /**
     * @return the value as passed with --local-address= command line argument. Null means the argument is not present
     * in the command line.
     */
    public String getLocalAddress() {

        return localAddress;
    }

    /**
     * The local address. May be null.
     */
    public InetAddress getLocalInetAddress() {

        //
        // this value has to be "resolved" from the string configuration
        //

        return localInetAddress;
    }

    /**
     * @return the value as passed with --local-port= command line argument. Null means the argument is not present in
     * the command line.
     */
    public Integer getLocalPort() {

        return localPort;
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

    /**
     * May return null.
     */
    public String getPayload() {

        return payload;
    }

    @Override
    public String toString() {

        String s = "configuration:\n\n";

        s += "     -Djava.net.preferIPv4Stack  " + Boolean.getBoolean("java.net.preferIPv4Stack") + "\n";
        s += "     mode                        " + mode + "\n";
        s += "     protocol                    " + protocol + "\n";
        s += "     interface                   " + interf + "\n";
        s += "     network interface           " + networkInterface + "\n";
        s += "     network interface name      " + getInterfaceName() + "\n";
        s += "     network interface addresses " + getNetworkInterfaceAddresses() + "\n";
        s += "     local address               " + localAddress + "\n";
        s += "     local internet address      " + Util.inetAddressToString(localInetAddress) + "\n";
        s += "     local port                  " + localPort + "\n";
        s += "     address                     " + address + "\n";
        s += "     internet address            " + Util.inetAddressToString(inetAddress) + "\n";
        s += "     port                        " + port + "\n";

        return s;
    }

    // Public that should have been package protected, made public for testing -----------------------------------------

    public void setAddress(String s) {

        this.address = s;
    }

    public void setProtocol(Protocol p) {

        this.protocol = p;
    }

    public void setMode(Mode m) {

        this.mode = m;
    }

    public void setNetworkInterface(NetworkInterface n) {

        this.networkInterface = n;
    }

    public void setPort(Integer i) {

        this.port = i;
    }

    // Package protected -----------------------------------------------------------------------------------------------

    void setInterface(String s) {

        this.interf = s;
    }


    void setLocalPort(Integer i) {

        this.localPort = i;
    }

    void setLocalAddress(String s) {

        this.localAddress = s;
    }

    /**
     * @param mustExist if true, the network interface must be specified, otherwise the method throws an exception; if
     *                  false, a non-existent network interface is a noop.
     */
    void initializeAndValidateNetworkInterface(boolean mustExist) throws UserErrorException {

        String i = getInterface();

        if (i == null) {

            if (mustExist) {

                throw new UserErrorException("missing required network interface, use --interface= to specify");
            }

            return;
        }

        try {

            setNetworkInterface(NetworkInterface.getByName(i));
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
                        setNetworkInterface(ni);
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
     * As a side effect, it extracts the port information, if exists, and turns the address string into an InetAddress,
     * if it exists.
     *
     * @param mustExist if true, the address must be specified, otherwise the method throws an exception; if
     *                  false, a non-existent address is a noop.
     *
     */
    void initializeAndValidateAddress(boolean mustExist) throws UserErrorException {

        String a = getAddress();

        if (a == null) {

            if (mustExist) {

                throw new UserErrorException("missing required address, use --address=...");
            }

            return;
        }

        int i = a.indexOf(":");
        String ps = null;

        if (i != -1) {

            ps = a.substring(i + 1);
            a = a.substring(0, i);
        }

        try {

            inetAddress = InetAddress.getByName(a);
        }
        catch(Exception e) {

            throw new UserErrorException("invalid address " + i, e);
        }

        if (ps != null) {

            try {

                setPort(Integer.parseInt(ps));
            }
            catch(Exception e) {

                throw new UserErrorException("invalid port value " + ps + ", not an integer");
            }
        }
    }

    void initializeAndValidateLocalAddress() throws UserErrorException {

        String a = getLocalAddress();

        if (a == null) {

            return;
        }

        try {

            localInetAddress = InetAddress.getByName(a);
        }
        catch(Exception e) {

            throw new UserErrorException("invalid local address " + a, e);
        }
    }

    /**
     * The protocol must be specified.
     */
    void validateProtocol() throws UserErrorException {

        if (getProtocol() == null) {

            throw new UserErrorException("a protocol must be specified with --protocol=udp|multicast|tcp|...");
        }
    }

    /**
     * The port must be specified and have a valid value.
     */
    void validatePort() throws UserErrorException {

        Integer port = getPort();

        if (port == null) {

            throw new UserErrorException("a port must be specified with --port=... or as part of the address");
        }

        Util.validatePort(port);
    }

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
