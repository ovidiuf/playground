package com.novaordis.playground.infinispan.command;

import com.novaordis.playground.infinispan.Command;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="mailto:ovidiu@novaordis.com">Ovidiu Feodorov</a>
 *
 * Copyright 2012 Nova Ordis LLC
 */
public class Help implements Command
{
    // Constants -----------------------------------------------------------------------------------

    private static final Logger log = Logger.getLogger(Help.class);

    // Static --------------------------------------------------------------------------------------

    // Attributes ----------------------------------------------------------------------------------

    // Constructors --------------------------------------------------------------------------------

    // Command implementation ----------------------------------------------------------------------

    @Override
    public void execute() throws Exception
    {
        // read all classes from the same package dynamically, until then add them manually to
        // this array
        List<Command> commands = Arrays.asList(
            new Help(),
            new Exit(),
            new com.novaordis.playground.infinispan.command.List(),
            new Size(),
            new Get(),
            new Put(),
            new LoadAndDistribute(),
            new LaunchDistributedCallable(),
            new Lock(),
            new Begin(),
            new Commit(),
            new RollBack());


        System.out.println();
        System.out.println("Starts an Infinispan node and connects it to the cluster. The node is programmatically");
        System.out.println("configured to run two caches ('SOURCE-CACHE' and 'DESTINATION-CACHE'). The cache");
        System.out.println("configuration must be specified into an Infinsipan XML configuration file that");
        System.out.println("must be available in the classpath. The configuration file name is passed to the");
        System.out.println("runtime as value of the 'playground.infinispan.node.config.file' environment variable.");
        System.out.println();
        System.out.println("Available commands:");

        for(Command c: commands)
        {
            System.out.println();
            System.out.println(c.getHelp());
        }

        System.out.println();
    }

    /**
     * @see com.novaordis.playground.infinispan.Command#getHelp()
     */
    @Override
    public String getHelp()
    {
        return
            //2345678901234567890123456789012345678901234567890123456789012345678901234567890
            " help - displays all available commands and their syntax.";
    }

    // Public --------------------------------------------------------------------------------------

    // Package protected ---------------------------------------------------------------------------

    // Protected -----------------------------------------------------------------------------------

    // Private -------------------------------------------------------------------------------------

    // Inner classes -------------------------------------------------------------------------------

}
