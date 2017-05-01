package io.novaordis.playground.jee.ejb.ejb2rest.restclient;

/**
 * Defines method(s) for obtaining rest clients.
 */
public interface RestClientFactory {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    /**
     * Returns a client proxy for the requested serviceInterface.
     *
     * Authentication mechanism is automatically determined based on contextual information that is expected to be
     * present when inside of a service.
     */
    <T> T get(Class<T> c);

}
