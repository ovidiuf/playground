package io.novaordis.playground.jee.ejb.ejb2rest.restclient;

import org.apache.http.HttpHost;
import org.apache.http.protocol.BasicHttpContext;

import javax.enterprise.inject.Alternative;

/**
 * Configurator that provides no authentication mechanism.
 */
@Alternative
class NoAuthenticationConfigurator implements Configurator {

    @Override
    public BasicHttpContext createContext(final HttpHost targetHost) {

        return new BasicHttpContext();
    }
}
