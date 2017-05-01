package io.novaordis.playground.jee.ejb.ejb2rest.caller;

import io.novaordis.playground.jee.ejb.ejb2rest.common.Callee;
import io.novaordis.playground.jee.ejb.ejb2rest.restclient.RestClientFactory;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

@SuppressWarnings("unused")
public class RestClientProducer {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    @Inject
    private RestClientFactory restClientFactory;

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    @Produces
    public Callee callee() {

        return restClientFactory.get(Callee.class);
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
