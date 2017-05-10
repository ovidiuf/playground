package io.novaordis.playground.jboss.infinispan.tllm;

import io.novaordis.playground.jboss.infinispan.tllm.cacheops.CacheOperation;
import org.infinispan.Cache;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfiguration;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.infinispan.transaction.LockingMode;
import org.infinispan.transaction.TransactionMode;
import org.infinispan.transaction.lookup.GenericTransactionManagerLookup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * The entry point for cache access.
 *
 * The servlet bootstraps the cache, translates CLI commands into cache operations.
 *
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/9/16
 */
public class JDGAccessServlet extends HttpServlet {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(JDGAccessServlet.class);

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
        // Configuration externalization
        //

        String jdgConfiguration = System.getProperty("playground.jdg.configuration");

        log.info("configuring the cache from " + jdgConfiguration);

        File jdgConfigurationFile = new File(jdgConfiguration);

        if (!jdgConfigurationFile.isFile() || !jdgConfigurationFile.canRead()) {

            throw new IllegalStateException(
                    "the cache configuration file " + jdgConfiguration +
                            " does not exist or is not readable - was the PoC correctly configured?");
        }

        //
        // Configure and build the cache manager
        //

        GlobalConfigurationBuilder gcb = new GlobalConfigurationBuilder();

        gcb.
                transport().
                defaultTransport().
                clusterName(CLUSTER_NAME).
                // the jgroups.xml file will be deployed within WEB-INF/classes
                addProperty("configurationFile", jdgConfiguration).
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
                cacheMode(CacheMode.DIST_SYNC).
                transaction().transactionMode(TransactionMode.TRANSACTIONAL).
                transactionManagerLookup(new GenericTransactionManagerLookup()).
                lockingMode(LockingMode.PESSIMISTIC).
                useSynchronization(true);

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

            CacheOperation i = CacheOperation.parse(req);

            result = i.execute(cache);
        }
        catch(UserErrorException e) {

            String msg = e.getMessage();

            log.error(msg);
            result = "[error]: " + msg;
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
