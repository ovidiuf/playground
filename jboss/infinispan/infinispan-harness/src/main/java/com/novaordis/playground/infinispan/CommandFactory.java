package com.novaordis.playground.infinispan;

import com.novaordis.playground.infinispan.command.Begin;
import com.novaordis.playground.infinispan.command.Commit;
import com.novaordis.playground.infinispan.command.Exit;
import com.novaordis.playground.infinispan.command.Get;
import com.novaordis.playground.infinispan.command.Help;
import com.novaordis.playground.infinispan.command.LaunchDistributedCallable;
import com.novaordis.playground.infinispan.command.List;
import com.novaordis.playground.infinispan.command.LoadAndDistribute;
import com.novaordis.playground.infinispan.command.Lock;
import com.novaordis.playground.infinispan.command.Noop;
import com.novaordis.playground.infinispan.command.Put;
import com.novaordis.playground.infinispan.command.RollBack;
import com.novaordis.playground.infinispan.command.Size;
import org.apache.log4j.Logger;

/**
 * @author <a href="mailto:ovidiu@novaordis.com">Ovidiu Feodorov</a>
 *
 * Copyright 2012 Nova Ordis LLC
 */
public class CommandFactory
{
    // Constants -----------------------------------------------------------------------------------

    private static final Logger log = Logger.getLogger(CommandFactory.class);

    // Static --------------------------------------------------------------------------------------

    public static Command get(String line, CacheAccess ca) throws Exception
    {
        line = line != null ? line.trim() : null;

        if ("".equals(line) || line == null)
        {
            return new Noop();
        }
        else if ("exit".equals(line))
        {
            return new Exit();
        }
        else if ("help".equals(line))
        {
            return new Help();
        }
        else if ("list".equals(line))
        {
            return new List(ca);
        }
        else if ("size".equals(line))
        {
            return new Size(ca);
        }
        else if (line.startsWith("get"))
        {
            return new Get(line.substring(3).trim(), ca);
        }
        else if (line.startsWith("put"))
        {
            return new Put(line.substring(3).trim(), ca);
        }
        else if (line.startsWith("ld ") || line.startsWith("l "))
        {
            return new LoadAndDistribute(line, ca);
        }
        else if (line.startsWith("call"))
        {
            return new LaunchDistributedCallable(line.substring(4).trim(), ca);
        }
        else if (line.startsWith("lock"))
        {
            return new Lock(line.substring("lock".length()).trim(), ca);
        }
        else if (line.startsWith("begin"))
        {
            return new Begin(line.substring("begin".length()).trim(), ca);
        }
        else if (line.startsWith("commit"))
        {
            return new Commit(line.substring("commit".length()).trim(), ca);
        }
        else if (line.startsWith("rollback"))
        {
            return new RollBack(line.substring("rollback".length()).trim(), ca);
        }
        else
        {
            log.error("unknown command '" + line + "'");
            return new Noop();
        }
    }




    // Attributes ----------------------------------------------------------------------------------

    // Constructors --------------------------------------------------------------------------------

    private CommandFactory()
    {
    }

    // Public --------------------------------------------------------------------------------------

    // Package protected ---------------------------------------------------------------------------

    // Protected -----------------------------------------------------------------------------------

    // Private -------------------------------------------------------------------------------------

    // Inner classes -------------------------------------------------------------------------------
}
