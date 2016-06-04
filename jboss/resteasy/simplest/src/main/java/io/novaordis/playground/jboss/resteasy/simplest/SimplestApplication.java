package io.novaordis.playground.jboss.resteasy.simplest;

import io.novaordis.playground.jboss.resteasy.simplest.resource.SimplestResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 9/25/15
 */
public class SimplestApplication extends Application {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(SimplestApplication.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private Set<Class<?>> classes;

    // Constructors ----------------------------------------------------------------------------------------------------

    public SimplestApplication() {

        classes = new HashSet<>();
        classes.add(SimplestResource.class);

        log.info("SimplestApplication instance constructed");
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
