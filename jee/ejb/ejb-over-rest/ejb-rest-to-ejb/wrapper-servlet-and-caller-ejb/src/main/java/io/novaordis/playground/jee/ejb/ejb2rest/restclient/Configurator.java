package io.novaordis.playground.jee.ejb.ejb2rest.restclient;

import org.apache.http.HttpHost;
import org.apache.http.protocol.BasicHttpContext;

/**
 * Defines a method for creating configured http contexts.
 */
interface Configurator {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    /**
     * Creates a {@link BasicHttpContext} for the target {@link HttpHost}.
     *
     * Used by {@link RestClientFactoryImpl} to delegate this task, which varies depending on the
     * context in which a client is created.
     */
    BasicHttpContext createContext(HttpHost targetHost);

}
