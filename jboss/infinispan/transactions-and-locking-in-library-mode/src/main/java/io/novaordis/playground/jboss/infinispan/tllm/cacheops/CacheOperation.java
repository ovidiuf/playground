package io.novaordis.playground.jboss.infinispan.tllm.cacheops;

import io.novaordis.playground.jboss.infinispan.tllm.Options;
import org.infinispan.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a GET, PUT, or a more complex sequence of API calls that happen in a transaction, etc.
 *
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 5/4/17
 */
public abstract class CacheOperation {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(CacheOperation.class);

    // Static ----------------------------------------------------------------------------------------------------------

    public static CacheOperation parse(HttpServletRequest req) throws Exception {

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

    protected CacheOperation(Options options) {

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
