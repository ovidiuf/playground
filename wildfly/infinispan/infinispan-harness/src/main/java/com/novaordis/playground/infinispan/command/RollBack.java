package com.novaordis.playground.infinispan.command;

import com.novaordis.playground.infinispan.CacheAccess;
import com.novaordis.playground.infinispan.Command;
import org.apache.log4j.Logger;
import org.infinispan.transaction.lookup.DummyTransactionManagerLookup;

import javax.transaction.UserTransaction;

/**
 * Rolls back a "JTA" transaction - it's actually a DummyTransaction, that only lives in Memory
 *
 * @author <a href="mailto:ovidiu@novaordis.com">Ovidiu Feodorov</a>
 *
 * Copyright 2012 Nova Ordis LLC
 */
public class RollBack implements Command
{
    // Constants -----------------------------------------------------------------------------------

    private static final Logger log = Logger.getLogger(RollBack.class);

    // Static --------------------------------------------------------------------------------------

    // Attributes ----------------------------------------------------------------------------------

    private CacheAccess ca;

    // Constructors --------------------------------------------------------------------------------

    public RollBack(String args, CacheAccess ca) throws Exception
    {
        this.ca = ca;
    }

    RollBack()
    {
    }

    // Command implementation ----------------------------------------------------------------------

    @Override
    public void execute() throws Exception
    {
        UserTransaction ut = DummyTransactionManagerLookup.getUserTransaction();

        ut.rollback();

        System.out.println("> transaction rolled back: " + ut);
    }

    @Override
    public String getHelp()
    {
        return
            " rollback - rolls back a dummy JTA transaction";

    }

    // Public --------------------------------------------------------------------------------------

    // Package protected ---------------------------------------------------------------------------

    // Protected -----------------------------------------------------------------------------------

    // Private -------------------------------------------------------------------------------------

    // Inner classes -------------------------------------------------------------------------------

}
