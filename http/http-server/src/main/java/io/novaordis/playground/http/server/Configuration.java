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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

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
    private Boolean persistentConnection;

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

        List<String> argList = new ArrayList<>();
        argList.addAll(Arrays.asList(args).subList(1, args.length));

        for(Iterator<String> ai = argList.iterator(); ai.hasNext(); ) {

            String crt = ai.next();
            if (crt.startsWith("persistent-connection=")) {

                crt = crt.substring("persistent-connection=".length());

                if ("false".equalsIgnoreCase(crt)) {

                    persistentConnection = false;
                }
                else if (!"true".equalsIgnoreCase(crt)) {

                    throw new Exception("invalid persistent-connection value: " + crt);
                }

                ai.remove();
            }
            else if (documentRoot == null) {

                documentRoot = new File(crt);
                ai.remove();
            }
        }

        if (!argList.isEmpty()) {

            s = "";
            for(String a: argList) {
                s += a + " ";
            }

            throw new Exception("unknown arguments: " + s);
        }

        //
        // defaults
        //

        if (documentRoot == null) {

            documentRoot = new File(".");
        }

        if (persistentConnection == null) {

            persistentConnection = true;
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

    /**
     * @{linktourl https://kb.novaordis.com/index.php/HTTP_Persistent_Connections}
     */
    public boolean isPersistentConnections() {

        return persistentConnection;
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
