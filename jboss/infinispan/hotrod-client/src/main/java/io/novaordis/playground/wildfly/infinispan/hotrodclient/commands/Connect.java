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

import io.novaordis.playground.wildfly.infinispan.hotrodclient.Console;
import io.novaordis.playground.wildfly.infinispan.hotrodclient.Runtime;
import io.novaordis.playground.wildfly.infinispan.hotrodclient.UserErrorException;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.Configuration;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;

import java.util.StringTokenizer;

/**
 * Command creates a new RemoteCacheManager instance and gets the default cache, which then places in Runtime.
 *
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 5/29/16
 */
@SuppressWarnings("unused")
public class Connect extends CommandBase {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private String host;
    private int port;

    // null means "default cache"
    private String cacheName;

    // Constructors ----------------------------------------------------------------------------------------------------

    public Connect() {

        this.host = "localhost";
        this.port = 11222;
    }

    // Command implementation ------------------------------------------------------------------------------------------

    @Override
    public void execute(String restOfTheLine) throws Exception {

        Runtime runtime = getRuntime();

        RemoteCacheManager remoteCacheManager = runtime.getRemoteCacheManager();

        if (remoteCacheManager != null) {

            Console.info("already connected");
            return;
        }

        parseOptions(restOfTheLine);

        Console.info("connecting to " + host + ":" + port + "(" + (cacheName == null ? "DEFAULT CACHE" : cacheName) + ") ...");

        Configuration c = new ConfigurationBuilder().addServer().host(host).port(port).build();

        remoteCacheManager = new RemoteCacheManager(c);

        RemoteCache cache;

        if (cacheName == null) {

            cache = remoteCacheManager.getCache();
        }
        else {

            cache = remoteCacheManager.getCache(cacheName);
        }

        if (cache == null) {

            throw new UserErrorException("no such cache: " + cacheName);
        }

        runtime.setCache(cache);
        Console.info("cache \"" + cache.getName() + "\" installed in runtime");
    }

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    private void parseOptions(String line) {

        if (line == null) {
            return;
        }

        StringTokenizer st = new StringTokenizer(line, " ");

        while(st.hasMoreTokens()) {

            String tok = st.nextToken();

            //
            // host:port[:cache-name]
            //

            int i = tok.indexOf(':');
            if (i == -1) {
                host = tok;
            }
            else {
                host = tok.substring(0, i);
                tok = tok.substring(i + 1).trim();

                i = tok.indexOf(':');

                if (i == -1) {

                    port = Integer.parseInt(tok);
                }
                else {

                    port = Integer.parseInt(tok.substring(0, i));
                    cacheName = tok.substring(i + 1);
                }
            }
        }
    }

    // Inner classes ---------------------------------------------------------------------------------------------------

}
