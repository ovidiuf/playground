/*
 * Copyright (c) 2017 Nova Ordis LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.novaordis.playground.temp.jdgpoc;

import org.infinispan.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.TransactionManager;
import java.util.List;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 5/4/17
 */
public class Put extends CacheApiInvocation {

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

    // CacheApiInvocation overrides ------------------------------------------------------------------------------------

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
