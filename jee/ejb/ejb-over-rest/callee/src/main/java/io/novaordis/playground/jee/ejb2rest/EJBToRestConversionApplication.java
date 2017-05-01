package io.novaordis.playground.jee.ejb2rest;

import io.novaordis.playground.jee.ejb2rest.callee.CalleeImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Application;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 5/1/17
 */
@SuppressWarnings("unused")
public class EJBToRestConversionApplication extends Application {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(EJBToRestConversionApplication.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private Set<Class<?>> classes;

    // Constructors ----------------------------------------------------------------------------------------------------

    public EJBToRestConversionApplication() {

        classes = new HashSet<>();
        classes.add(CalleeImpl.class);

        log.info("EJBToRestConversionApplication deployed");
    }


    // Application overrides -------------------------------------------------------------------------------------------

    @Override
    public Set<Object> getSingletons() {

        return Collections.emptySet();
    }

    @Override
    public Set<Class<?>> getClasses() {

        return classes;
    }

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
