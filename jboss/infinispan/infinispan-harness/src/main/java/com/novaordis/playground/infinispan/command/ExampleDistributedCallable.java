package com.novaordis.playground.infinispan.command;

import org.apache.log4j.Logger;
import org.infinispan.Cache;
import org.infinispan.distexec.DistributedCallable;

import java.io.Serializable;
import java.util.Set;

/**
 * @author <a href="mailto:ovidiu@novaordis.com">Ovidiu Feodorov</a>
 */
class ExampleDistributedCallable implements DistributedCallable, Serializable
{
    // Constants -----------------------------------------------------------------------------------

    private static final Logger log = Logger.getLogger(ExampleDistributedCallable.class);

    private static final long serialVersionUID = 0123L;

    // Static --------------------------------------------------------------------------------------

    // Attributes ----------------------------------------------------------------------------------

    private long sleep;
    private Cache cache;


    // Constructors --------------------------------------------------------------------------------

    ExampleDistributedCallable()
    {
        this.sleep = 20;
    }

    // DistributedCallable implementation ----------------------------------------------------------

    @Override
    public void setEnvironment(Cache cache, Set inputKeys)
    {
        this.cache = cache;
        log.info(this + " setEnvironment(" + cache.getName() + ", " + inputKeys + ")");
    }

    @Override
    public DistributedCallableResponse call() throws Exception
    {
        log.info(this + ".call()");
        log.info(this + " sleeping " + sleep + " seconds");
        Thread.sleep(sleep * 1000L);
        log.info(this + " done sleeping");

        String address = cache.getCacheManager().getAddress().toString();
        return new DistributedCallableResponse(address, "payload");
    }

    // Public --------------------------------------------------------------------------------------

    @Override
    public String toString()
    {
        return "EDC[" + Integer.toHexString(System.identityHashCode(this)) + "]";
    }

    // Package protected ---------------------------------------------------------------------------

    // Protected -----------------------------------------------------------------------------------

    // Private -------------------------------------------------------------------------------------

    // Inner classes -------------------------------------------------------------------------------

}
