package com.novaordis.playground.infinispan.command;

import com.novaordis.playground.infinispan.CacheAccess;
import com.novaordis.playground.infinispan.Command;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.text.Format;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.Future;

/**
 * A "complex" scenario that involves loading a source cache and sending a distributable across
 * the cluster. It is also possible to "load only".
 *
 * @author <a href="mailto:ovidiu@novaordis.com">Ovidiu Feodorov</a>
 *
 * Copyright 2012 Nova Ordis LLC
 */
public class LoadAndDistribute implements Command
{
    // Constants -----------------------------------------------------------------------------------

    private static final Logger log = Logger.getLogger(LoadAndDistribute.class);

    // Static --------------------------------------------------------------------------------------

    // Attributes ----------------------------------------------------------------------------------

    private CacheAccess ca;
    private int keyCount;
    private int threadCount;
    private boolean loadOnly;

    // Constructors --------------------------------------------------------------------------------

    public LoadAndDistribute(String line, CacheAccess ca)
    {
        this.ca = ca;

        StringTokenizer st = new StringTokenizer(line, " ");

        // "l" or "ld"
        String command = st.nextToken();

        this.keyCount = 10;
        this.threadCount = 1;

        if (st.hasMoreTokens())
        {
            // the number of keys
            this.keyCount = Integer.parseInt(st.nextToken());
        }

        if (st.hasMoreTokens())
        {
            // the thread count
            this.threadCount = Integer.parseInt(st.nextToken());
        }

        this.loadOnly = "l".equals(command);
    }

    LoadAndDistribute()
    {
    }

    // Command implementation ----------------------------------------------------------------------


    @Override
    public void execute() throws Exception
    {
        if (loadOnly)
        {
            log.info("loading only ...");
        }
        else
        {
            log.info("loading and distributing ...");
        }

        String[] keys = new String[keyCount];

        Format df = new DecimalFormat("0000000");

        // load "source" cache on a single thread
        String cacheName = "SOURCE-CACHE";


        long t0 = System.currentTimeMillis();

        for(int i = 0; i < keyCount; i ++)
        {
            keys[i] = "TEST_KEY_" + df.format(i);
            ca.put(cacheName, keys[i], keys[i]);
        }

        long t1 = System.currentTimeMillis();

        log.info("'" + cacheName + "' loaded in " + (t1 - t0) + " ms");

        if (loadOnly)
        {
            return;
        }

        ProcessingDistributable d = new ProcessingDistributable(threadCount, "DESTINATION-CACHE");
        List<Future<Long>> futures = ca.submitDistributable(cacheName, d, keys);

        log.info("distributables sent, waiting for processing to finish ...");

        for(Future<Long> f: futures)
        {
            f.get(); // not really interested in results
        }

        log.info("distributed processing done, verifying that processing results are in the destination cache ...");

        for(int i = 0; i < keyCount; i ++)
        {
            String key = "PROCESSED_DISTRIBUTED_KEY_" + i;
            String s = (String)ca.get("DESTINATION-CACHE", key);
            if (s == null)
            {
                throw new Exception("key " + key + " not found");
            }
            if (!s.equals(key))
            {
                throw new Exception("unexpected value for key " + key);
            }
        }

        System.out.println("> ALL " + keyCount + " KEYS FOUND, ALL VALUES ARE CORRECT, TEST SUCCESSFUL");
    }

    @Override
    public String getHelp()
    {
        return
            //2345678901234567890123456789012345678901234567890123456789012345678901234567890
            " l [key-count|10] [thread-count|1] - 'load only' the specified number of keys  \n" +
            "        in 'SOURCE-CACHE' from the specified number of threads.\n\n" +
            " ld [key-count|10] [thread-count|1] - 'load and distribute' the specified number\n" +
            "        of keys from the specified number of threads, according the following\n" +
            "        scenario: parallel load the keys in 'SOURCE-CACHE' and then use a distributed\n" +
            "        callable to remote load 'DESTINATION-CACHE'";

    }

    // Public --------------------------------------------------------------------------------------

    // Package protected ---------------------------------------------------------------------------

    // Protected -----------------------------------------------------------------------------------

    // Private -------------------------------------------------------------------------------------

    // Inner classes -------------------------------------------------------------------------------
}
