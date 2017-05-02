package io.novaordis.playground.jee.ejb.ejb2rest.restclient;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.protocol.BasicHttpContext;

/**
 * Uses basic authentication with the provided username and password.
 */
@SuppressWarnings("unused")
public class BasicAuthConfigurator implements Configurator {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    /**
     * Username for authentication.
     */
    private final String username;

    /**
     * Password for authentication.
     */
    private final String password;

    // Constructors ----------------------------------------------------------------------------------------------------

    public BasicAuthConfigurator(String username, String password) {

        this.username = username;
        this.password = password;
    }

    // Configuration implementation ------------------------------------------------------------------------------------

    @Override
    public BasicHttpContext createContext(HttpHost targetHost) {

        final AuthCache authCache = new BasicAuthCache();

        final BasicScheme basicAuth = new BasicScheme();
        authCache.put(targetHost, basicAuth);

        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();

        // OPTIMIZED
        credentialsProvider
                .setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));

        // NON-OPTIMIZED
//        credentialsProvider.setCredentials(
//                new AuthScope(targetHost.getHostName(), targetHost.getPort()),
//                new UsernamePasswordCredentials(username, password));

        final BasicHttpContext context = new BasicHttpContext();
        context.setAttribute(HttpClientContext.AUTH_CACHE, authCache);
        context.setAttribute(HttpClientContext.CREDS_PROVIDER, credentialsProvider);

        return context;
    }

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
