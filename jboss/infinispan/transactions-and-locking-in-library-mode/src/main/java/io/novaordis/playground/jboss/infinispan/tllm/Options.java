package io.novaordis.playground.jboss.infinispan.tllm;

import io.novaordis.playground.jboss.infinispan.tllm.cacheops.Lock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * The set of options for a CLI invocation, sent by the CLI as HTTP Query String.
 *
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 5/4/17
 */
public class Options {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(Lock.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private Integer sleepSecs;

    private boolean nonTransactional;

    // Constructors ----------------------------------------------------------------------------------------------------

    public Options(HttpServletRequest req) throws Exception {

        nonTransactional = false;

        for(Enumeration<String> names= req.getParameterNames(); names.hasMoreElements(); ) {

            String name = names.nextElement();
            String value = req.getParameter(name);

            if ("sleep-secs".equalsIgnoreCase(name)) {

                try {

                    sleepSecs = Integer.parseInt(value);
                }
                catch(Exception e) {

                    throw new UserErrorException("'" + value + "' is not a valid sleep value");
                }
            }
            else if ("non-transactional".equalsIgnoreCase(name)) {

                if ("true".equalsIgnoreCase(value)) {

                    nonTransactional = true;
                }
                else if (!"false".equalsIgnoreCase(name)) {

                    throw new UserErrorException("'" + value + "' is not a valid boolean");
                }
            }
            else {

                log.warn("unknown option '" + name + "', ignoring ...");
            }
        }
    }

    // Public ----------------------------------------------------------------------------------------------------------

    public boolean isNonTransactional() {

        return nonTransactional;
    }

    public boolean isTransactional() {

        return !nonTransactional;
    }

    /**
     * May return null, which means no sleep.
     */
    public Integer getSleepSecs() {

        return sleepSecs;
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
