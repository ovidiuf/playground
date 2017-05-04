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

package io.novaordis.playground.temp.jdgpoc;

import org.infinispan.Cache;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfiguration;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
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

    public static final String CLUSTER_NAME = "PLAYGROUND-CLUSTER";
    public static final String CACHE_NAME = "playground-cache";

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private EmbeddedCacheManager cacheManager;

    private Cache<String, String> cache;

    // Constructors ----------------------------------------------------------------------------------------------------

    // HttpServlet overrides -------------------------------------------------------------------------------------------

    @Override
    public void init() {

        //
        // Configure and build the cache manager
        //

        GlobalConfigurationBuilder gcb = new GlobalConfigurationBuilder();

        gcb.
                transport().
                defaultTransport().
                clusterName(CLUSTER_NAME).
                // the jgroups.xml file will be deployed within WEB-INF/classes
                addProperty("configurationFile", "jgroups.xml").
                globalJmxStatistics().
                allowDuplicateDomains(true).
                enable();

        GlobalConfiguration gc = gcb.build();

        this.cacheManager = new DefaultCacheManager(gc);

        //
        // Configure and build the cache
        //

        ConfigurationBuilder ccb = new ConfigurationBuilder();

        ccb.
                clustering().
                cacheMode(CacheMode.DIST_SYNC);

        Configuration cc = ccb.build();

        cacheManager.defineConfiguration(CACHE_NAME, cc);

        this.cache = cacheManager.getCache(CACHE_NAME);

        log.info(this + " initialized");
    }

    @Override
    public void destroy() {

        if (cacheManager != null) {

            cacheManager.stop();
        }

        log.info(this + " destroyed");
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        String result;

        try {

            CacheApiInvocation i = CacheApiInvocation.parse(req);

            result = i.execute(cache);
        }
        catch(Exception e) {

            throw new ServletException(e);
        }

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        out.println(result);
    }

    // Public ----------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {

        return "JDG Access Servlet[" + Integer.toHexString(System.identityHashCode(this)) + "]";
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
