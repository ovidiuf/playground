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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 3/14/17
 */
public class Util {

    // Constants -------------------------------------------------------------------------------------------------------

    public static final Pattern IPv4_FORMAT = Pattern.compile("([0-9]+)\\.([0-9]+)\\.([0-9]+)\\.([0-9]+)");

    // Static ----------------------------------------------------------------------------------------------------------

    /**
     * Attempts to interpret the address as a IPv4 or IPv6 byte[] address.
     *
     * @return the byte[4] or byte[16] or null if the conversion cannot be performed.
     *
     * @throws UserErrorException
     */
    public static byte[] addressToBytes(String address) throws UserErrorException {

        Matcher m = IPv4_FORMAT.matcher(address);

        if (m.find()) {

            byte[] result = new byte[4];

            String b = m.group(1);
            result[0] = (byte)Integer.parseInt(b);
            b = m.group(2);
            result[1] = (byte)Integer.parseInt(b);
            b = m.group(3);
            result[2] = (byte)Integer.parseInt(b);
            b = m.group(4);
            result[3] = (byte)Integer.parseInt(b);

            return result;
        }

        return null;
    }

    public static boolean identical(byte[] b, byte[] b2) {

        if (b == null || b2 == null) {

            return false;
        }

        if (b.length != b2.length) {

            return false;
        }

        for(int i = 0; i < b.length; i ++) {

            if (b[i] != b2[i]) {

                return false;
            }
        }

        return true;
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
