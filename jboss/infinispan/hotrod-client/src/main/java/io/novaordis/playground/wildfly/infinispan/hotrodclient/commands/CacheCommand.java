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

package io.novaordis.playground.wildfly.infinispan.hotrodclient.commands;

import io.novaordis.playground.wildfly.infinispan.hotrodclient.Runtime;
import io.novaordis.playground.wildfly.infinispan.hotrodclient.UserErrorException;
import org.infinispan.client.hotrod.RemoteCache;

/**
 * A cache command, needs a an active cache in Runtime.
 *
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 5/29/16
 */
@SuppressWarnings("unused")
public abstract class CacheCommand extends CommandBase {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Command implementation ------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    protected RemoteCache insureConnected() throws Exception {

        Runtime runtime = getRuntime();
        RemoteCache cache = runtime.getCache();
        if (cache == null) {
            throw new UserErrorException("not connected");
        }

        return cache;
    }

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
