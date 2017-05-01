package io.novaordis.playground.jee.ejb.ejb2rest.restclient;

import org.apache.http.HttpHost;
import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.protocol.BasicHttpContext;

import javax.servlet.http.Cookie;

public class UseCookieConfigurator implements Configurator {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private final Cookie cookie;

    // Constructors ----------------------------------------------------------------------------------------------------

    public UseCookieConfigurator(Cookie cookie) {

        this.cookie = cookie;
    }

    // Configurator implementation -------------------------------------------------------------------------------------

    @Override
    public BasicHttpContext createContext(final HttpHost targetHost) {

        final CookieStore cookieStore = new BasicCookieStore();

        final BasicClientCookie clientCookie =
                new BasicClientCookie(cookie.getName(), cookie.getValue());
        clientCookie.setDomain(targetHost.getHostName());
        clientCookie.setPath("/");
        cookieStore.addCookie(clientCookie);

        final BasicHttpContext context = new BasicHttpContext();
        context.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);

        return context;
    }

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
