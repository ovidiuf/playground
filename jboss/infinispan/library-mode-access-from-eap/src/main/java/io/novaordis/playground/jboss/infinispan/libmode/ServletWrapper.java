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

package io.novaordis.playground.jboss.infinispan.libmode;

import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.Configuration;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.commons.api.BasicCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/9/16
 */
public class ServletWrapper extends HttpServlet {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(ServletWrapper.class);

    // Static ----------------------------------------------------------------------------------------------------------

    public static final String JDG_SERVER_IP_ADDRESS = "172.16.153.101";
    public static final int JDG_SERVER_IP_PORT = 11222;

    // Attributes ------------------------------------------------------------------------------------------------------

    private RemoteCacheManager remoteCacheManager;

    private BasicCache<String, String> cache;

    // Constructors ----------------------------------------------------------------------------------------------------

    // HttpServlet overrides -------------------------------------------------------------------------------------------

    @Override
    public void init() {

        ConfigurationBuilder cb = new ConfigurationBuilder();

        cb.tcpNoDelay(true)
                .connectionPool()
                .numTestsPerEvictionRun(3)
                .testOnBorrow(false)
                .testOnReturn(false)
                .testWhileIdle(true)
                .addServer()
                .host(JDG_SERVER_IP_ADDRESS)
                .port(JDG_SERVER_IP_PORT);

        Configuration c = cb.build();

        this.remoteCacheManager = new RemoteCacheManager(c);

        this.cache = remoteCacheManager.getCache();

        log.info(this + " initialized");
    }

    @Override
    public void destroy() {

        if (remoteCacheManager != null) {

            remoteCacheManager.stop();
        }

        log.info(this + " destroyed");
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        cache.put("A", "B");

        String s = cache.get("A");

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        out.println("content retrieved from remote JDG cache: " + s);
    }

    // Public ----------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {

        return "ServletExample[" + Integer.toHexString(System.identityHashCode(this)) + "]";
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
