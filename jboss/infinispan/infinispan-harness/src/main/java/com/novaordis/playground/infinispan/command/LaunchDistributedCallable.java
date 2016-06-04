package com.novaordis.playground.infinispan.command;

import com.novaordis.playground.infinispan.CacheAccess;
import com.novaordis.playground.infinispan.Command;
import org.apache.log4j.Logger;
import org.infinispan.Cache;
import org.infinispan.distexec.DefaultExecutorService;
import org.infinispan.distexec.DistributedExecutorService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * @author <a href="mailto:ovidiu@novaordis.com">Ovidiu Feodorov</a>
 *
 * Copyright 2012 Nova Ordis LLC
 */
public class LaunchDistributedCallable implements Command
{
    // Constants -----------------------------------------------------------------------------------

    private static final Logger log = Logger.getLogger(LaunchDistributedCallable.class);

    // Static --------------------------------------------------------------------------------------

    // Attributes ----------------------------------------------------------------------------------

    private CacheAccess ca;
    private List<String> keys;
    private boolean submitEverywhere;

    // Constructors --------------------------------------------------------------------------------

    public LaunchDistributedCallable(String args, CacheAccess ca) throws Exception
    {
        this.ca = ca;

        keys = new ArrayList<String>();

        StringTokenizer st = new StringTokenizer(args, ", ");

        if (!st.hasMoreTokens())
        {
            submitEverywhere = true;
        }
        else
        {
            String firstToken = st.nextToken();

            if ("all".equals(firstToken))
            {
                submitEverywhere = true;
            }
            else if ("one".equals(firstToken))
            {
                submitEverywhere = false;
            }
            else
            {
                // it's the first key
                submitEverywhere = true;
                keys.add(firstToken);
            }

            while(st.hasMoreTokens())
            {
                keys.add(st.nextToken());
            }
        }
    }

    LaunchDistributedCallable()
    {
    }

    // Command implementation ----------------------------------------------------------------------

    @Override
    public void execute() throws Exception
    {
        Cache c = ca.getCache("SOURCE-CACHE");

        DistributedExecutorService des = new DefaultExecutorService(c);

        Callable callable = new ExampleDistributedCallable();
        //Callable<String> callable = new ExampleCallable<String>();

        List<Future<DistributedCallableResponse>> results = null;

        if (submitEverywhere)
        {
            if (keys.isEmpty())
            {
                System.out.println("> submitting everywhere, no input keys");
                results = des.submitEverywhere(callable);
                System.out.println("> submission everywhere, no input keys, ok");
            }
            else
            {
                results = des.submitEverywhere(callable, keys);
                System.out.println("> submission to all nodes ok, key set " + keys);
            }
        }
        else
        {
            Future<DistributedCallableResponse> result = des.submit(callable, keys);
            System.out.println("> submission to one node ok, key set " + keys);
            results = Arrays.asList(result);
        }

        for (Future<DistributedCallableResponse> f : results)
        {
            System.out.println("> got response from " + f.get().getNodeName());
        }

        System.out.println("> execution ok");
    }

    @Override
    public String getHelp()
    {
        return
            //2345678901234567890123456789012345678901234567890123456789012345678901234567890
            " call [one|all] [key1, key2, ...] - submits the distributed callable on a randomly\n" +
            "        chosen node (if the first argument is 'one') or to all nodes (if the second\n" +
            "        argument is 'all'). If both 'one' or 'all' are missing, 'all' is assumed by\n" +
            "        default. If some keys are specified, those will be the input keys, otherwise\n" +
            "        the callable is submitted without the initial set of keys.";

    }

    // Public --------------------------------------------------------------------------------------

    // Package protected ---------------------------------------------------------------------------

    // Protected -----------------------------------------------------------------------------------

    // Private -------------------------------------------------------------------------------------

    // Inner classes -------------------------------------------------------------------------------

}
