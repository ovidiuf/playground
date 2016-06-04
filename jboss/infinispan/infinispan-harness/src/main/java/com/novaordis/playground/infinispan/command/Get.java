package com.novaordis.playground.infinispan.command;

import com.novaordis.playground.infinispan.CacheAccess;
import com.novaordis.playground.infinispan.Command;
import org.apache.log4j.Logger;

/**
 * @author <a href="mailto:ovidiu@novaordis.com">Ovidiu Feodorov</a>
 *
 * Copyright 2012 Nova Ordis LLC
 */
public class Get implements Command
{
    // Constants -----------------------------------------------------------------------------------

    private static final Logger log = Logger.getLogger(Get.class);

    // Static --------------------------------------------------------------------------------------

    // Attributes ----------------------------------------------------------------------------------

    private CacheAccess ca;
    private String key;

    // Constructors --------------------------------------------------------------------------------

    public Get(String getArgs, CacheAccess ca) throws Exception
    {
        this.ca = ca;
        this.key = getArgs.trim();
    }

    Get()
    {
    }

    // Command implementation ----------------------------------------------------------------------

    @Override
    public void execute() throws Exception
    {
        System.out.println("> " + key + "=" + ca.get("SOURCE-CACHE", key));
    }

    @Override
    public String getHelp()
    {
        return
            //2345678901234567890123456789012345678901234567890123456789012345678901234567890
            " get <key-name> - displays the value associated with the key, if any, or null";

    }

    // Public --------------------------------------------------------------------------------------

    // Package protected ---------------------------------------------------------------------------

    // Protected -----------------------------------------------------------------------------------

    // Private -------------------------------------------------------------------------------------

    // Inner classes -------------------------------------------------------------------------------

}
