package com.novaordis.playground.infinispan;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author <a href="mailto:ovidiu@novaordis.com">Ovidiu Feodorov</a>
 *
 * Copyright 2012 Nova Ordis LLC
 */
public class Main
{
    // Constants -----------------------------------------------------------------------------------

    private static final Logger log = Logger.getLogger(Main.class);

    // Static --------------------------------------------------------------------------------------

    public static void main(String[] args) throws Exception
    {
        String nodeName = extractNodeName(args);

        String configurationFileName = System.getProperty("playground.infinispan.node.config.file");

        if (configurationFileName == null)
        {
            throw new Exception("no 'playground.infinispan.node.config.file' found, set the system property to contain a valid configuration file name (the file must be available in classpath) and retry");
        }


        ProcessingNode n = new ProcessingNode(nodeName, configurationFileName);

        n.run();

        System.out.println("> configuration file: " + configurationFileName);

        readCommandsFromCommandLineAndPassThemToNode(n);
    }

    // Attributes ----------------------------------------------------------------------------------

    // Constructors --------------------------------------------------------------------------------

    // Public --------------------------------------------------------------------------------------

    // Package protected ---------------------------------------------------------------------------

    // Protected -----------------------------------------------------------------------------------

    // Private -------------------------------------------------------------------------------------

    private static String extractNodeName(String[] args)
    {
        if (args.length == 0)
        {
            return null;
        }

        System.setProperty("playground.infinispan.node.name", args[0]);
        return args[0];
    }

    private static void readCommandsFromCommandLineAndPassThemToNode(CacheAccess ca)
        throws Exception
    {
        BufferedReader br = null;

        try
        {
            br = new BufferedReader(new InputStreamReader(System.in));
            while(true)
            {
                System.out.print("> ");
                String line = br.readLine();

                try
                {
                    Command c = CommandFactory.get(line, ca);
                    c.execute();
                }
                catch(Exception e)
                {
                    log.error("command failed", e);
                }
            }
        }
        finally
        {
            if (br != null)
            {
                br.close();
            }
        }
    }

    // Inner classes -------------------------------------------------------------------------------

}
