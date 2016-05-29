package com.novaordis.playground.infinispan;

import org.apache.log4j.Logger;
import org.infinispan.Cache;
import org.infinispan.config.Configuration;
import org.infinispan.config.GlobalConfiguration;
import org.infinispan.configuration.cache.LegacyConfigurationAdaptor;
import org.infinispan.configuration.global.LegacyGlobalConfigurationAdaptor;
import org.infinispan.distexec.DefaultExecutorService;
import org.infinispan.distexec.DistributedExecutorService;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.transaction.TransactionMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * @author <a href="mailto:ovidiu@novaordis.com">Ovidiu Feodorov</a>
 *
 * Copyright 2012 Nova Ordis LLC
 */
public class ProcessingNode implements CacheAccess
{
    // Constants -----------------------------------------------------------------------------------

    private static final Logger log = Logger.getLogger(ProcessingNode.class);

    // Static --------------------------------------------------------------------------------------

    // Attributes ----------------------------------------------------------------------------------

    private String name;
    private String configurationFileName;

    private Map<String, Cache> caches;

    // Constructors --------------------------------------------------------------------------------

    /**
     * The first argument is the name of the node.
     */
    public ProcessingNode(String name, String configurationFileName) throws Exception
    {
        if (name == null)
        {
            this.name = "ANONYMOUS";
        }
        else
        {
            this.name = name;
        }

        this.configurationFileName = configurationFileName;

        caches = new HashMap<String, Cache>();
    }

    // CacheAccess implementation ------------------------------------------------------------------

    @Override
    public Set<Object> getKeys(String cacheName)
    {
        return getCache(cacheName).keySet();
    }

    @Override
    public int size(String cacheName)
    {
        return getCache(cacheName).size();
    }

    @Override
    public Object get(String cacheName, Object key)
    {
        return getCache(cacheName).get(key);
    }

    @Override
    public void put(String cacheName, Object key, Object value)
    {
        getCache(cacheName).put(key, value);
    }

    @Override
    public List<Future<Long>> submitDistributable(String cacheName,
                                                  Callable<Long> callable,
                                                  String... keys)
    {
        Cache c = getCache(cacheName);

        DistributedExecutorService des  = new DefaultExecutorService(c);

        return des.submitEverywhere(callable, keys);

        // TODO executors will probably leak, find a better solution where I shut down an
        // executor that is not in use anymore.
    }

    @Override
    public Cache<Object, Object> getCache(String cacheName)
    {
        Cache c = caches.get(cacheName);

        if (c == null)
        {
            throw new IllegalArgumentException("no such cache '" + cacheName + "'");
        }

        return c;
    }

    // Public --------------------------------------------------------------------------------------

    @Override
    public String toString()
    {
        return "ProcessingNode[" + name + "]";
    }

    // Package protected ---------------------------------------------------------------------------

    // Protected -----------------------------------------------------------------------------------

    // Private -------------------------------------------------------------------------------------

    void run() throws Exception
    {
        init();

        log.info(this + " running ...");

    }

    private void init() throws Exception
    {
        log.info(this + " initializing ...");

        //DefaultCacheManager cm = configureProgrammatically();
        DefaultCacheManager cm = configureDeclaratively();


        Cache sc = cm.getCache("SOURCE-CACHE");
        caches.put("SOURCE-CACHE", sc);

        Cache dc = cm.getCache("DESTINATION-CACHE");
        caches.put("DESTINATION-CACHE", dc);
    }

    private DefaultCacheManager configureProgrammatically()
    {
        GlobalConfiguration gc = GlobalConfiguration.getClusteredDefault();
        gc.setClusterName("TEST-CLUSTER");

        Configuration dc = new Configuration();
        dc.setCacheMode(Configuration.CacheMode.DIST_SYNC);

        return new DefaultCacheManager(
            LegacyGlobalConfigurationAdaptor.adapt(gc), LegacyConfigurationAdaptor.adapt(dc));
    }

    private DefaultCacheManager configureDeclaratively() throws Exception
    {
        return new DefaultCacheManager(configurationFileName);
    }

    // Inner classes -------------------------------------------------------------------------------
}
