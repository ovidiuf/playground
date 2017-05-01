/*
 * Copyright (c) 2017 Nova Ordis LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.novaordis.playground.jee.ejb.ejb2rest.restclient;

import org.apache.http.HttpHost;
import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.protocol.BasicHttpContext;

import javax.servlet.http.Cookie;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 5/1/17
 */
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
