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

package io.novaordis.playground.http.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The local port to listen on must be specified as the first argument.
 *
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/4/17
 */
public class Main {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    public static void main(String[] args) throws Exception {

        int port = extractPort(args);

        Server server = new Server(port);

        server.listen();

        log.info("http server ready to accept connections ...");
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    private static int extractPort(String[] args) throws Exception {

        if (args.length == 0) {

            throw new Exception("the value of the port to listen on must be specified as the first argument");
        }

        String s = args[0];

        try {

            return Integer.parseInt(s);
        }
        catch(Exception e) {

            throw new Exception("\"" + s + "\" is not a valid port value");
        }
    }

    // Inner classes ---------------------------------------------------------------------------------------------------

}
