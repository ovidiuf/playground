package com.novaordis.playground.infinispan.command;

import com.novaordis.playground.infinispan.Command;

/**
 * @author <a href="mailto:ovidiu@novaordis.com">Ovidiu Feodorov</a>
 *
 * Copyright 2012 Nova Ordis LLC
 */
public class Exit implements Command
{
    // Constants -----------------------------------------------------------------------------------

    // Static --------------------------------------------------------------------------------------

    // Attributes ----------------------------------------------------------------------------------

    // Constructors --------------------------------------------------------------------------------

    // Command implementation ----------------------------------------------------------------------

    @Override
    public void execute() throws Exception
    {
        System.exit(0);
    }

    @Override
    public String getHelp()
    {
        return
            //2345678901234567890123456789012345678901234567890123456789012345678901234567890
            " exit - properly terminates this node, by shutting down the JGroups channel.";

    }

    // Public --------------------------------------------------------------------------------------

    // Package protected ---------------------------------------------------------------------------

    // Protected -----------------------------------------------------------------------------------

    // Private -------------------------------------------------------------------------------------

    // Inner classes -------------------------------------------------------------------------------

}
