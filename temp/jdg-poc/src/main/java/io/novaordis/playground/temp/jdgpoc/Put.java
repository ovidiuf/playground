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

import javax.swing.*;
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
    private long sleep = 20000L;

    // Constructors ----------------------------------------------------------------------------------------------------

    public Put(List<String> uriTokens) {

        if (uriTokens.size() < 1) {

            throw new IllegalArgumentException("a key must be specified");
        }

        key = uriTokens.get(0);

        if (uriTokens.size() < 2) {

            throw new IllegalArgumentException("a value must be specified");
        }

        value = uriTokens.get(1);
    }

    // CacheApiInvocation overrides ------------------------------------------------------------------------------------

    @Override
    public String execute(Cache<String, String> cache) throws Exception {

        TransactionManager tm = Util.getTransactionManager();

        tm.begin();

        log.info("locking key " + key + "...");

        boolean lock = cache.getAdvancedCache().lock(key);

        log.info("lock " + (lock ? "acquired" : "not acquired"));

        log.info("put(" + key + "=" + value + ") " + (Util.inTransaction() ? "transactionally" : "non-transactionally"));

        String result = cache.put(key, value);

        log.info("sleeping for " + (sleep / 1000L) + " seconds ...");

        Thread.sleep(sleep);

        log.info("done sleeping, committing transaction");

        tm.commit();

        return result;
    }

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
