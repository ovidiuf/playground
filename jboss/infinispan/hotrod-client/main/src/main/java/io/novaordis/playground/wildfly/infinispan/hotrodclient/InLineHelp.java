/*
 * Copyright (c) 2016 Nova Ordis LLC
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

package io.novaordis.playground.wildfly.infinispan.hotrodclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 5/31/16
 */
public class InLineHelp {

    // Constants -------------------------------------------------------------------------------------------------------

    public static final String HELP_FILE_NAME="help.txt";

    // Static ----------------------------------------------------------------------------------------------------------

    public static String get() throws UserErrorException {

        InputStream is = InLineHelp.class.getClassLoader().getResourceAsStream(HELP_FILE_NAME);

        if (is == null) {
            throw new UserErrorException(
                    "no " + HELP_FILE_NAME + " file found on the classpath; this usually means the utility was not built or installed correctly");
        }

        String help = "";
        BufferedReader br = null;

        try
        {
            br = new BufferedReader(new InputStreamReader(is));
            String line;
            while((line = br.readLine()) != null)
            {
                help += line + "\n";
            }
        }
        catch (Exception e) {

            throw new IllegalStateException(e);
        }
        finally
        {
            if (br != null)
            {
                try {
                    br.close();
                }
                catch(IOException e) {
                    System.err.println("warn: failed to close the input stream");
                }
            }
        }

        return help;
    }

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
