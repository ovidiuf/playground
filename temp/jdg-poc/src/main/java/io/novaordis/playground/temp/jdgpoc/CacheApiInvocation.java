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

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a GET, PUT, etc.
 *
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 5/4/17
 */
public abstract class CacheApiInvocation {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(CacheApiInvocation.class);

    // Static ----------------------------------------------------------------------------------------------------------

    public static CacheApiInvocation parse(HttpServletRequest req) throws Exception {

        String uri = req.getRequestURI();

        List<String> tokens = new ArrayList<>(Arrays.asList(uri.split("/")));

        for(Iterator<String> i = tokens.iterator(); i.hasNext(); ) {

            if (i.next().trim().isEmpty()) {

                i.remove();
            }
        }

        String command = tokens.get(1);

        Options options = new Options(req);

        if ("put".equalsIgnoreCase(command)) {

            return new Put(tokens.subList(2, tokens.size()), options);
        }
        else if ("get".equalsIgnoreCase(command)) {

            return new Get(tokens.subList(2, tokens.size()), options);
        }
        else if ("lock".equalsIgnoreCase(command)) {

            return new Lock(tokens.subList(2, tokens.size()), options);
        }
        else {

            throw new IllegalArgumentException("unknown command \"" + command + "\"");
        }

    }

    // Attributes ------------------------------------------------------------------------------------------------------

    private Options options;

    // Constructors ----------------------------------------------------------------------------------------------------

    protected CacheApiInvocation(Options options) {

        this.options = options;
    }

    // Public ----------------------------------------------------------------------------------------------------------

    public abstract String execute(Cache<String, String> cache) throws Exception;

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    protected Options getOptions() {

        return options;
    }

    protected void sleepIfNeeded() throws InterruptedException {

        if (options.getSleepSecs() == null) {

            return;
        }

        int sleepSecs = getOptions().getSleepSecs();

        log.info("sleeping for " + sleepSecs + " seconds ...");

        Thread.sleep(1000L * sleepSecs);

        log.info("done sleeping");
    }

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
