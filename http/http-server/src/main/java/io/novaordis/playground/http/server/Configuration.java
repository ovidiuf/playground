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

import java.io.File;

/**
 * Server Configuration.
 *
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/6/17
 */
public class Configuration {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private int port;
    private File documentRoot;

    // Constructors ----------------------------------------------------------------------------------------------------

    public Configuration(String[] args) throws Exception {

        if (args.length == 0) {

            throw new Exception("the value of the port to listen on must be specified as the first argument");
        }

        String s = args[0];

        try {

            port = Integer.parseInt(s);
        }
        catch(Exception e) {

            throw new Exception("\"" + s + "\" is not a valid port value");
        }

        if (args.length >= 2) {

            documentRoot = new File(args[1]);
        }
        else {

            documentRoot = new File(".");
        }
    }

    // Public ----------------------------------------------------------------------------------------------------------

    /**
     * @return the local port to listen on for HTTP connections
     */
    public int getPort() {

        return port;
    }

    /**
     * Never return null.
     */
    public File getDocumentRoot() {

        return documentRoot;
    }


    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
