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

import io.novaordis.playground.wildfly.infinispan.hotrodclient.UserErrorException;
import org.infinispan.client.hotrod.RemoteCache;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 5/29/16
 */
@SuppressWarnings("unused")
public class Put extends CacheCommand {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Command implementation ------------------------------------------------------------------------------------------

    @Override
    public void execute(String restOfTheLine) throws Exception {

        if (restOfTheLine == null || restOfTheLine.trim().length() == 0) {

            throw new UserErrorException("key name missing");
        }

        int i = restOfTheLine.indexOf(' ');
        if (i == -1) {
            throw new UserErrorException("key value pair missing");
        }

        String keyName = restOfTheLine.substring(0, i);
        restOfTheLine = restOfTheLine.substring(i + 1);
        String keyValue = restOfTheLine.replaceAll(" .*$", "");

        RemoteCache defaultCache = insureConnected();

        defaultCache.put(keyName, keyValue);
    }

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
