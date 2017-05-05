package io.novaordis.playground.jboss.infinispan.tllm.cacheops;

import io.novaordis.playground.jboss.infinispan.tllm.Options;
import io.novaordis.playground.jboss.infinispan.tllm.Util;
import org.infinispan.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.TransactionManager;
import java.util.List;

/**
 * A cache lock API call.
 *
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 5/4/17
 */
public class Lock extends CacheOperation {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(Lock.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private String key;

    // Constructors ----------------------------------------------------------------------------------------------------

    public Lock(List<String> uriTokens, Options options) {

        super(options);

        if (uriTokens.size() < 1) {
            throw new IllegalArgumentException("a key must be specified");
        }

        key = uriTokens.get(0);
    }

    // CacheOperation overrides ----------------------------------------------------------------------------------------

    @Override
    public String execute(Cache<String, String> cache) throws Exception {

        TransactionManager tm = Util.getTransactionManager();

        tm.begin();

        log.info("attempting to lock " + key + " ...");

        boolean lockAcquired = cache.getAdvancedCache().lock(key);

        log.info("lock was " + (lockAcquired ? "" : "NOT ") + "acquired");

        tm.commit();

        return "" + lockAcquired;
    }

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
