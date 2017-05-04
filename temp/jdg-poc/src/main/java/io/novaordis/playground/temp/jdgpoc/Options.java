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

package io.novaordis.playground.temp.jdgpoc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 5/4/17
 */
public class Options {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(Lock.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private Integer sleepSecs;

    private boolean nonTransactional;

    // Constructors ----------------------------------------------------------------------------------------------------

    public Options(HttpServletRequest req) throws Exception {

        nonTransactional = false;

        for(Enumeration<String> names= req.getParameterNames(); names.hasMoreElements(); ) {

            String name = names.nextElement();
            String value = req.getParameter(name);

            if ("sleep-secs".equalsIgnoreCase(name)) {

                try {

                    sleepSecs = Integer.parseInt(value);
                }
                catch(Exception e) {

                    throw new UserErrorException("'" + value + "' is not a valid sleep value");
                }
            }
            else if ("non-transactional".equalsIgnoreCase(name)) {

                if ("true".equalsIgnoreCase(value)) {

                    nonTransactional = true;
                }
                else if (!"false".equalsIgnoreCase(name)) {

                    throw new UserErrorException("'" + value + "' is not a valid boolean");
                }
            }
            else {

                log.warn("unknown option '" + name + "', ignoring ...");
            }
        }
    }

    // Public ----------------------------------------------------------------------------------------------------------

    public boolean isNonTransactional() {

        return nonTransactional;
    }

    public boolean isTransactional() {

        return !nonTransactional;
    }

    /**
     * May return null, which means no sleep.
     */
    public Integer getSleepSecs() {

        return sleepSecs;
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
