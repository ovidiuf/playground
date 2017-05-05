package io.novaordis.playground.jboss.infinispan.tllm.cacheops;

import io.novaordis.playground.jboss.infinispan.tllm.Options;
import io.novaordis.playground.jboss.infinispan.tllm.UserErrorException;
import io.novaordis.playground.jboss.infinispan.tllm.Util;
import org.infinispan.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.TransactionManager;
import java.util.List;

/**
 * A cache put.
 *
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 5/4/17
 */
public class Put extends CacheOperation {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(Put.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private String key;
    private String value;

    // Constructors ----------------------------------------------------------------------------------------------------

    public Put(List<String> uriTokens, Options options) throws UserErrorException {

        super(options);

        if (uriTokens.size() < 1) {

            throw new UserErrorException("a key must be specified");
        }

        key = uriTokens.get(0);

        if (uriTokens.size() < 2) {

            throw new UserErrorException("a value must be specified for key '" + key + "'");
        }

        value = uriTokens.get(1);
    }

    // CacheOperation overrides ----------------------------------------------------------------------------------------

    @Override
    public String execute(Cache<String, String> cache) throws Exception {

        boolean transactional = getOptions().isTransactional();

        if (transactional) {

            log.info("beginning transaction");
            Util.getTransactionManager().begin();
        }

        boolean success = false;

        try {

            log.info("put " + key + "=" + value + " " + (Util.inTransaction() ? "transactionally" : "non-transactionally"));

            String result = cache.put(key, value);

            sleepIfNeeded();

            success = true;

            return result;
        }
        finally {

            if (transactional) {

                TransactionManager tm = Util.getTransactionManager();

                if (success) {

                    log.info("committing transaction");
                    tm.commit();
                }
                else {

                    log.info("rolling back transaction");
                    tm.rollback();
                }
            }
        }
    }

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
