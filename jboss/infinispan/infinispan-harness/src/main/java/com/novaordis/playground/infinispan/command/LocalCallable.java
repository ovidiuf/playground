package com.novaordis.playground.infinispan.command;

import org.apache.log4j.Logger;
import org.infinispan.Cache;

import java.util.concurrent.Callable;

/**
 * @author <a href="mailto:ovidiu@novaordis.com">Ovidiu Feodorov</a>
 *
 * Copyright 2012 Nova Ordis LLC
 */

class LocalCallable implements Callable<Long>
{
    // Constants -----------------------------------------------------------------------------------

     private static final Logger log = Logger.getLogger(ProcessingDistributable.class);

    // Static --------------------------------------------------------------------------------------

    // Attributes ----------------------------------------------------------------------------------

    private String key;
    private Cache<String, String> destinationCache;

    // Constructors --------------------------------------------------------------------------------

    public LocalCallable(Cache<String, String> destinationCache, String key)
    {
        this.key = key;
        this.destinationCache = destinationCache;
    }

    // Callable implementation ---------------------------------------------------------------------

    public Long call() throws Exception
    {
        log.info(this + " processing " + key + " ...");

        String s = "PROCESSED_" + key;

        destinationCache.put(s, s);

        String verification = destinationCache.get(s);
        if (verification == null)
        {
            throw new Exception("did not find key " + s + " in the destination cache");
        }

        if (!verification.equals(s))
        {
            throw new Exception("key " + s + " associated with the wrong value");
        }

        return new Long(1);
    }

    // Public --------------------------------------------------------------------------------------

    @Override
    public String toString()
    {
        return "Local[" + key + "]";
    }

    // Package protected ---------------------------------------------------------------------------

    // Protected -----------------------------------------------------------------------------------

    // Private -------------------------------------------------------------------------------------

    // Inner classes -------------------------------------------------------------------------------

}
