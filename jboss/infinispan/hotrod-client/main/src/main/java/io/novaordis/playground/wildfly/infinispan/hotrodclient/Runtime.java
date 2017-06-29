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

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;

/**
 * The container for the runtime state. Spans the life of th eclient.
 *
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 5/29/16
 */
public class Runtime {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private RemoteCache currentCache;

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    public void setCache(RemoteCache rc) {

        if (currentCache != null) {
            throw new IllegalStateException("Default cache already set: " + currentCache);
        }

        currentCache = rc;
    }

    /**
     * May be null if the "connect" command was not executed or it failed.
     */
    public RemoteCache getCache() {
        return currentCache;
    }

    public RemoteCacheManager getRemoteCacheManager() {

        RemoteCache dc = getCache();

        if (dc == null) {
            return null;
        }

        return dc.getRemoteCacheManager();
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
