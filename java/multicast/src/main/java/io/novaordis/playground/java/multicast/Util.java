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

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 3/14/17
 */
public class Util {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    public static Action parseCommandLine(String[] args) throws Exception {

        if (args == null || args.length == 0) {

            throw new UserErrorException("must specify whether to listen or to send");
        }

        String command = args[0];
        String[] rest = new String[args.length - 1];
        System.arraycopy(args, 1, rest, 0, rest.length);

        if ("listen".equalsIgnoreCase(command)) {

            return new Listen(rest);

        }
        else if ("send".equalsIgnoreCase(command)) {

            return new Send(rest);
        }
        else {

            throw new UserErrorException("unknown command: \"" + command + "\"");
        }
    }

    public static InetSocketAddress colonSeparatedStringToSocketAddress(String s) throws UserErrorException {

        if (s == null) {

            throw new IllegalArgumentException("null string");
        }

        int i = s.indexOf(':');

        if (i == -1) {

            throw new UserErrorException(
                    "the multicast address and port must be specified in addr:port format, but we got \"" + s + "\"");
        }

        String addr = s.substring(0, i);
        String p = s.substring(i + 1);
        int port;

        try {

            port = Integer.parseInt(p);
        }
        catch(Exception e) {

            throw new UserErrorException("invalid port " + p);
        }

        return new InetSocketAddress(addr, port);
    }

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    private Util() {
    }

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
