package com.novaordis.playground.infinispan.command;

import org.apache.log4j.Logger;
import org.infinispan.Cache;
import org.infinispan.distexec.DistributedCallable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author <a href="mailto:ovidiu@novaordis.com">Ovidiu Feodorov</a>
 *
 * Copyright 2012 Nova Ordis LLC
 */
public class ProcessingDistributable
    implements DistributedCallable<String, String, Long>, Serializable
{
    // Constants -----------------------------------------------------------------------------------

    private static final Logger log = Logger.getLogger(ProcessingDistributable.class);

    // Static --------------------------------------------------------------------------------------

    // Attributes ----------------------------------------------------------------------------------

    private int threadCount;
    private String destinationCacheName;

    private transient Set<String> inputKeys;
    private transient Cache<String, String> destinationCache;

    // Constructors --------------------------------------------------------------------------------

    public ProcessingDistributable(int threadCount, String destinationCacheName)
    {
        this.threadCount = threadCount;
        this.destinationCacheName = destinationCacheName;
    }

    // DistributedCallable implementation ----------------------------------------------------------

    public void setEnvironment(Cache<String, String> c, Set<String> inputKeys)
    {
        log.info("setEnvironment(" + c.getName() + ", " + inputKeys);
        destinationCache = c.getCacheManager().getCache(destinationCacheName);
        this.inputKeys = inputKeys;
    }

    public Long call() throws Exception
    {
        log.info("processing on " + threadCount + " threads");

        ExecutorService pool = Executors.newFixedThreadPool(threadCount);

        final List<Future<Long>> futures = new ArrayList<Future<Long>>();

        for(String k: inputKeys)
        {
            futures.add(pool.submit(new LocalCallable(destinationCache, k)));
        }

        log.info("all local callable submitted, waiting for the parallel processing tasks to finish");

        for(Future<Long> f: futures)
        {
            f.get(); // we really don't care about the result, this can be implemented better
        }

        log.info("all parallel processing tasks are done, signaling back to the initiating node");

        return new Long(1);
    }

    // Public --------------------------------------------------------------------------------------

    // Package protected ---------------------------------------------------------------------------

    // Protected -----------------------------------------------------------------------------------

    // Private -------------------------------------------------------------------------------------

    // Inner classes -------------------------------------------------------------------------------

}
