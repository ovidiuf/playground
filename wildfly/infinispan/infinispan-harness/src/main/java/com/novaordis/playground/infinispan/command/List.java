package com.novaordis.playground.infinispan.command;

import com.novaordis.playground.infinispan.CacheAccess;
import com.novaordis.playground.infinispan.Command;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

/**
 * @author <a href="mailto:ovidiu@novaordis.com">Ovidiu Feodorov</a>
 *
 * Copyright 2012 Nova Ordis LLC
 */
public class List implements Command
{
    // Constants -----------------------------------------------------------------------------------

     private static final Logger log = Logger.getLogger(List.class);

    // Static --------------------------------------------------------------------------------------

    // Attributes ----------------------------------------------------------------------------------

    private CacheAccess ca;

    // Constructors --------------------------------------------------------------------------------

    public List(CacheAccess ca)
    {
        this.ca = ca;
    }

    List()
    {
    }

    // Command implementation ----------------------------------------------------------------------

    @Override
    public void execute() throws Exception
    {
            Set<Object> keys = ca.getKeys("SOURCE-CACHE");
            java.util.List list = new ArrayList(keys);
            Collections.sort(list);
            System.out.println("> " + list);
    }

    @Override
    public String getHelp()
    {
        return
            //2345678901234567890123456789012345678901234567890123456789012345678901234567890
            " list - displays the keys stored in the SOURCE-CACHE";

    }

    // Public --------------------------------------------------------------------------------------

    // Package protected ---------------------------------------------------------------------------

    // Protected -----------------------------------------------------------------------------------

    // Private -------------------------------------------------------------------------------------

    // Inner classes -------------------------------------------------------------------------------

}
