package io.novaordis.playground.jboss.infinispan.tllm.cacheops;

import io.novaordis.playground.jboss.infinispan.tllm.Options;
import io.novaordis.playground.jboss.infinispan.tllm.UserErrorException;
import io.novaordis.playground.jboss.infinispan.tllm.Util;
import org.infinispan.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * A cache get.
 *
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 5/4/17
 */
public class Get extends CacheOperation {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(Get.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private String key;

    // Constructors ----------------------------------------------------------------------------------------------------

    public Get(List<String> uriTokens, Options options) throws UserErrorException {

        super(options);

        if (uriTokens.size() < 1) {
            throw new UserErrorException("a key must be specified");
        }

        key = uriTokens.get(0);
    }

    // CacheOperation overrides ----------------------------------------------------------------------------------------

    @Override
    public String execute(Cache<String, String> cache) throws Exception {

        startATransactionIfWeWereConfiguredToDoSo();

        boolean success = false;

        try {

            log.info("get(" + key + ") " + (Util.inTransaction() ? "transactionally" : "non-transactionally"));

            String result = cache.get(key);

            success = true;

            return result;
        }
        finally {

            if (success) {

                commitTransactionIfPresent();
            }
            else {

                rollbackTransactionIfPresent();
            }
        }
    }

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
