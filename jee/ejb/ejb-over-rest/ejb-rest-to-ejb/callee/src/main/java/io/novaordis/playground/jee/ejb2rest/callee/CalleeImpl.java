package io.novaordis.playground.jee.ejb2rest.callee;

import io.novaordis.playground.jee.ejb.ejb2rest.common.Callee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 5/1/17
 */
@SuppressWarnings("unused")
//@Stateless
public class CalleeImpl implements Callee {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(CalleeImpl.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Callee implementation -------------------------------------------------------------------------------------------

    @Override
    public String businessMethodA(String arg) {

        log.info(this + " got businessMethodA(" + arg + ")");

        String result = "";

        for(int i = arg.length() - 1; i >= 0; i --) {

            result += arg.charAt(i);
        }

        return result;
    }

    // Public ----------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {

        return "Callee[" + Integer.toHexString(System.identityHashCode(this)) + "]";
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
