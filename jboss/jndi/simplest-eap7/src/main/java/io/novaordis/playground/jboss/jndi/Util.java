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

package io.novaordis.playground.jboss.jndi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.NameClassPair;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 2/7/17
 */
public class Util {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(Util.class);

    // Static ----------------------------------------------------------------------------------------------------------

    public static void logNameClassPair(NameClassPair ncp) {

        String s = "name: ";
        s += ncp.getName();

        s += ", class name: ";
        s += ncp.getClassName();

        s += ", name in namespace: ";

        try {

            s += ncp.getNameInNamespace();
        }
        catch(UnsupportedOperationException e) {

            s += "UNSUPPORTED";

        }

        s += ", ";
        s += (ncp.isRelative() ? "relative" : "NOT relative");

        log.info(s);
    }

    public static String getPrefix(int depth) {

        String prefix = "";
        String unit = "  ";

        for(int i = 0; i < depth; i ++) {

            prefix += unit;
        }

        return prefix;
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
