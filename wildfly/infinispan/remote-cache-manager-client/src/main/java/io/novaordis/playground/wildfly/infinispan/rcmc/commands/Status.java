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

package io.novaordis.playground.wildfly.infinispan.rcmc.commands;

import io.novaordis.playground.wildfly.infinispan.rcmc.Console;
import io.novaordis.playground.wildfly.infinispan.rcmc.Runtime;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ServerConfiguration;

import java.util.Iterator;
import java.util.List;

/**
 * Displays the status of the runtime
 *
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 5/29/16
 */
@SuppressWarnings("unused")
public class Status extends CommandBase {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Command implementation ------------------------------------------------------------------------------------------

    @Override
    public void execute(String restOfTheLine) throws Exception {

        Runtime runtime = getRuntime();
        RemoteCache rc = runtime.getDefaultCache();

        if (rc == null) {

            Console.info("not connected");
        }
        else {

            RemoteCacheManager rcm = rc.getRemoteCacheManager();

            String msg = "connected\n";

            msg += "  servers: ";

            List<ServerConfiguration> serverConfigurations = rcm.getConfiguration().servers();

            for (Iterator<ServerConfiguration> i = serverConfigurations.iterator(); i.hasNext(); ) {

                ServerConfiguration sc = i.next();

                msg += sc.host() + ":" + sc.port();

                if (i.hasNext()) {
                    msg += ", ";
                }
            }

            msg += "\n";
            msg += "  default cache: \"" + rc.getName() + "\"";

            Console.info(msg);
        }
    }

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
