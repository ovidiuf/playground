package io.novaordis.playground.jee.ejb.ejb2rest.restclient;

import org.jboss.resteasy.client.ClientExecutor;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.UriBuilder;

/**
 * Decorator for {@link ClientExecutor} which adds logging.
 */
public class LoggingClientExecutor implements ClientExecutor {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(LoggingClientExecutor.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    /**
     * The executor to which work will be delegated.
     */
    private final ClientExecutor target;

    // Constructors ----------------------------------------------------------------------------------------------------

    public LoggingClientExecutor(ClientExecutor target) {

        this.target = target;
    }

    // ClientExecutor implementation -----------------------------------------------------------------------------------

    @Override
    public void close() throws Exception {
        target.close();
    }

    @Override
    public ClientRequest createRequest(final String uriTemplate) {

        return target.createRequest(uriTemplate);
    }

    @Override
    public ClientRequest createRequest(final UriBuilder uriBuilder) {

        return target.createRequest(uriBuilder);
    }

    @Override
    public ClientResponse execute(final ClientRequest request) throws Exception {

        log.debug("{} {}", request.getHttpMethod(), request.getUri());
        return target.execute(request);
    }

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
