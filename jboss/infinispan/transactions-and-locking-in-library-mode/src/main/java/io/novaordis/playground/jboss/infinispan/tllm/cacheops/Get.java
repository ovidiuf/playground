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

        TransactionManager tm = Util.getTransactionManager();

        tm.begin();

        log.info("get(" + key + ") " + (Util.inTransaction() ? "transactionally" : "non-transactionally"));

        String result = cache.get(key);

        tm.commit();

        return result;
    }

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
