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

package io.novaordis.playground.jee.servlet.session.applicaton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * An "application type" to experiment with.
 *
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 6/12/16
 */
public class ApplicationType implements Serializable {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(ApplicationType.class);

    //
    // this is the type's version. For more details @see Serializable
    //
    private static final long serialVersionUID = 1L;

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private String state;

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    /**
     * Typed write.
     */
    public void write(String s) {

        log.info("write(" + s + ")");

        this.state = s;
    }

    /**
     * Typed read.
     */
    public String read() {

        log.info("read()");

        return state;
    }

    @Override
    public String toString() {

        String s = "";
        s += "ApplicationType[id=" + Integer.toHexString(System.identityHashCode(this)) + "]";
        s += "[version=" + serialVersionUID + "]";

        // state

        s += "[";

        boolean first = true;

        for(Field f: this.getClass().getDeclaredFields()) {

            if (Modifier.isStatic(f.getModifiers())) {
                continue;
            }

            if (first) {
                first = false;
            }
            else {
                s += ", ";
            }

            s += f.getName() + "=";

            try {
                s += f.get(this);
            }
            catch (Exception e) {

                s += "FAILURE";
            }
        }
        s += "]";

        return s;
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
