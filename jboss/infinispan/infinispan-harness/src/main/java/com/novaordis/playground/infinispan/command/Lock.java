package com.novaordis.playground.infinispan.command;

import com.novaordis.playground.infinispan.CacheAccess;
import com.novaordis.playground.infinispan.Command;
import org.apache.log4j.Logger;
import org.infinispan.AdvancedCache;
import org.infinispan.transaction.lookup.DummyTransactionManagerLookup;


import javax.transaction.UserTransaction;
import java.util.StringTokenizer;

/**
 * Locks a node using the AdvancedCache API.
 *
 * @author <a href="mailto:ovidiu@novaordis.com">Ovidiu Feodorov</a>
 *
 * Copyright 2012 Nova Ordis LLC
 */
public class Lock implements Command
{
    // Constants -----------------------------------------------------------------------------------

    private static final Logger log = Logger.getLogger(Lock.class);

    // Static --------------------------------------------------------------------------------------

    // Attributes ----------------------------------------------------------------------------------

    private CacheAccess ca;
    private String key;
    private String value;

    // Constructors --------------------------------------------------------------------------------

    public Lock(String args, CacheAccess ca) throws Exception
    {
        this.ca = ca;

        StringTokenizer st = new StringTokenizer(args);

        if (st.hasMoreTokens())
        {
            this.key = st.nextToken();
        }
        else
        {
            this.key = args.trim();
        }

        if (st.hasMoreTokens())
        {
            this.value = st.nextToken();
        }
    }

    Lock()
    {
    }

    // Command implementation ----------------------------------------------------------------------

    @Override
    public void execute() throws Exception
    {
        AdvancedCache ac = (AdvancedCache)ca.getCache("SOURCE-CACHE");

        // maipulate the JTA transaction with begin/commit/rollback

        ac.lock(key);

        System.out.println("> ok");

        // maipulate the JTA transaction with begin/commit/rollback

    }

    @Override
    public String getHelp()
    {
        return
            //2345678901234567890123456789012345678901234567890123456789012345678901234567890
            " lock <key-name> - attempts to lock the key using the AdvancedCache lock() API.";

    }

    // Public --------------------------------------------------------------------------------------

    // Package protected ---------------------------------------------------------------------------

    // Protected -----------------------------------------------------------------------------------

    // Private -------------------------------------------------------------------------------------

    // Inner classes -------------------------------------------------------------------------------

}
