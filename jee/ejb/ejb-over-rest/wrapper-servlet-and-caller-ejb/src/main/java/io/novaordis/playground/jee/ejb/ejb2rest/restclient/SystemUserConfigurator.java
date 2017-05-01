package io.novaordis.playground.jee.ejb.ejb2rest.restclient;

import org.apache.http.HttpHost;
import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.protocol.BasicHttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.inject.Alternative;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Uses system properties to determine the system username and password, and authenticates using
 * those credentials.
 */
@Alternative
class SystemUserConfigurator implements Configurator {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(SystemUserConfigurator.class);

    /**
     * System property for the system user's username.
     */
    static final String USERNAME_PROPERTY = "mp-rest-client.system.username";

    /**
     * System property for the system user's password.
     */
    static final String PASSWORD_PROPERTY = "mp-rest-client.system.password";

    // Static ----------------------------------------------------------------------------------------------------------

    /**
     * Cached contexts keyed by host.
     *
     * We cache the contexts for the system user, so that we can re-use the cookie store each one
     * contains.
     *
     * If we don't re-use the cookie store for requests made by the system user, then we run into a
     * situation where each service call made in the context of an MDB, or EJB scheduled method
     * results in a new session getting created, which would eventually lead to memory issues.
     */
    private static final Map<HttpHost, BasicHttpContext> CACHED_CONTEXTS =
            new ConcurrentHashMap<>(10);

    // Attributes ------------------------------------------------------------------------------------------------------

    /**
     * The {@link BasicAuthConfigurator} to be used for authentication.
     */
    private final BasicAuthConfigurator basicAuthConfigurator;


    // Constructors ----------------------------------------------------------------------------------------------------

    /**
     * Creates a {@link SystemUserConfigurator}.
     *
     * @throws NullPointerException if either of required system properties are absent.
     */
    SystemUserConfigurator() {

        final String username = System.getProperty(USERNAME_PROPERTY);
        final String password = System.getProperty(PASSWORD_PROPERTY);

        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {

            throw new IllegalStateException(
                    "Missing one or both of these required system properties: " + USERNAME_PROPERTY
                            + " and " + PASSWORD_PROPERTY);
        }

        basicAuthConfigurator = new BasicAuthConfigurator(username, password);
    }

    // Configurator implementation -------------------------------------------------------------------------------------

    @Override
    public BasicHttpContext createContext(final HttpHost targetHost) {

        BasicHttpContext context = CACHED_CONTEXTS.get(targetHost);

        if (context == null) {

            log.trace("creating and configuring new HttpContext for system user");
            context = basicAuthConfigurator.createContext(targetHost);

            final CookieStore cookieStore = new BasicCookieStore();
            context.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);

            log.trace("caching HttpContext for system user");
            CACHED_CONTEXTS.put(targetHost, context);
        }
        else {

            log.trace("using cached HttpContext for system user");
        }

        return context;
    }
}
