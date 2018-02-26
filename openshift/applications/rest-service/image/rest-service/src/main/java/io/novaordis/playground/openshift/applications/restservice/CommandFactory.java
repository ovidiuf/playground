/*
 * Copyright (c) 2018 Nova Ordis LLC
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

package io.novaordis.playground.openshift.applications.restservice;

import io.novaordis.playground.openshift.applications.restservice.command.Ping;
import io.novaordis.playground.openshift.applications.restservice.command.Version;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 2/26/18
 */
public class CommandFactory {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = new StdoutLogger(CommandFactory.class);

    // Static ----------------------------------------------------------------------------------------------------------

    public static Command build(Context context) throws Exception {

        String path = context.getRequest().getPathInfo();

        log.debug("path: " + path);

        if (path == null || "/".equals(path) || "/ping".equals(path) ) {

            return new Ping(context);
        }
        else if (path.startsWith("/version")) {

            return new Version(context);
        }

        throw new HttpException(404, "no known command can be inferred from " + path);
    }

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
