package com.novaordis.playground.infinispan.command;

import com.novaordis.playground.infinispan.CacheAccess;
import com.novaordis.playground.infinispan.Command;
import org.apache.log4j.Logger;
import org.infinispan.AdvancedCache;
import org.infinispan.transaction.lookup.DummyTransactionManagerLookup;

import javax.transaction.UserTransaction;
import java.util.StringTokenizer;

/**
 * Begins a "JTA" transaction - it's actually a DummyTransaction, that only lives in Memory
 *
 * @author <a href="mailto:ovidiu@novaordis.com">Ovidiu Feodorov</a>
 *
 * Copyright 2012 Nova Ordis LLC
 */
public class Begin implements Command
{
    // Constants -----------------------------------------------------------------------------------

    private static final Logger log = Logger.getLogger(Begin.class);

    // Static --------------------------------------------------------------------------------------

    // Attributes ----------------------------------------------------------------------------------

    private CacheAccess ca;

    // Constructors --------------------------------------------------------------------------------

    public Begin(String args, CacheAccess ca) throws Exception
    {
        this.ca = ca;
    }

    Begin()
    {
    }

    // Command implementation ----------------------------------------------------------------------

    @Override
    public void execute() throws Exception
    {
        UserTransaction ut = DummyTransactionManagerLookup.getUserTransaction();

        ut.begin();

        System.out.println("> transaction begun: " + ut);
    }

    @Override
    public String getHelp()
    {
        return
            " begin - starts a dummy JTA transaction";

    }

    // Public --------------------------------------------------------------------------------------

    // Package protected ---------------------------------------------------------------------------

    // Protected -----------------------------------------------------------------------------------

    // Private -------------------------------------------------------------------------------------

    // Inner classes -------------------------------------------------------------------------------

}
