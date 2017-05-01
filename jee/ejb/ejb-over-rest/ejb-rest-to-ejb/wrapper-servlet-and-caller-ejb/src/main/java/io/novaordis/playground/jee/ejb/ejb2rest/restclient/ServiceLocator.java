package io.novaordis.playground.jee.ejb.ejb2rest.restclient;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Responsible for determining base URLs of services.
 */
class ServiceLocator {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    /**
     * Builds the name for the system property whose value defines the base URL of the requested service.
     */
    static String getSystemPropertyNameForService(final Class<?> serviceInterface) {

        return "mp-rest-client.base-url." + serviceInterface.getSimpleName();
    }

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    /**
     * Returns the {@link ServiceLocation} for the requested serviceInterface.
     *
     * Currently implemented as a simple lookup in system properties. Intended to be enhanced when we decide to
     * implement service discovery with something like Zookeeper or Consul.
     */
    @SuppressWarnings("JavadocReference")
    public URL lookupService(final Class<?> serviceInterface) {

        final String systemProperty = getSystemPropertyNameForService(serviceInterface);

        final String baseUrl = System.getProperty(systemProperty);

        if (baseUrl == null || baseUrl.isEmpty()) {

            throw new IllegalStateException("Missing system property: " + systemProperty);
        }

        try {

            return new URL(baseUrl);
        }
        catch (final MalformedURLException e) {

            throw new IllegalStateException("Malformed URL in system property: " + systemProperty + " = " + baseUrl, e);
        }
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
