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
    private String interf;
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

                interf = arg.substring("--interface=".length());

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

    public Mode getMode() {

        return mode;
    }

    public Protocol getProtocol() {

        return protocol;
    }

    /**
     * @return the value as passed with --address= command line argument. Null means the argument is not present in the
     * command line.
     */
    public String getAddress() {

        return address;
    }

    /**
     * @return the value as passed with --interface= command line argument. Null means the argument is not present in
     * the command line.
     */
    public String getInterface() {

        return interf;
    }

    /**
     * @return the value as passed with --port= command line argument. Null means the argument is not present in
     * the command line.
     */
    public Integer getPort() {

        return port;
    }

    @Override
    public String toString() {

        String s = "configuration:\n";
        s += "     mode      " + mode + "\n";
        s += "     interface " + interf + "\n";
        s += "     protocol  " + protocol + "\n";
        s += "     address   " + address + "\n";
        s += "     port      " + port + "\n";

        return s;
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
