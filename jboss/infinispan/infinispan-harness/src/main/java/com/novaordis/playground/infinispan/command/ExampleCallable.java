package com.novaordis.playground.infinispan.command;

import org.apache.log4j.Logger;
import org.infinispan.Cache;
import org.infinispan.distexec.DistributedCallable;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * @author <a href="mailto:ovidiu@novaordis.com">Ovidiu Feodorov</a>
 */
class ExampleCallable<T> implements Callable<T>, Serializable
{
    // Constants -----------------------------------------------------------------------------------

    private static final Logger log = Logger.getLogger(ExampleCallable.class);

    private static final long serialVersionUID = 0123L;

    // Static --------------------------------------------------------------------------------------

    // Attributes ----------------------------------------------------------------------------------

    // Constructors --------------------------------------------------------------------------------

    // Callable implementation ---------------------------------------------------------------------

    @Override
    public T call() throws Exception
    {
        log.info(this + ".call()");
        return null;
    }

    // Public --------------------------------------------------------------------------------------

    @Override
    public String toString()
    {
        return "EC[" + Integer.toHexString(System.identityHashCode(this)) + "]";
    }

    // Package protected ---------------------------------------------------------------------------

    // Protected -----------------------------------------------------------------------------------

    // Private -------------------------------------------------------------------------------------

    // Inner classes -------------------------------------------------------------------------------

}
