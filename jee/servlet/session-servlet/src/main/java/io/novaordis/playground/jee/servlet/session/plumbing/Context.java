/*
 * Copyright (c) 2016 Nova Ordis LLC
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

package io.novaordis.playground.jee.servlet.session.plumbing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * The central point of contact for a request/response exchange context. Contains the references for the request and
 * response themselves, the session, if it exists, and exposes accessors for various environment elements.
 *
 * It performs basic verifications on initialization. The constructor may throw HttpException if it finds
 * irregularities. Those will be communicated to the client.
 *
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 6/9/16
 */
public class Context {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(Context.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private Console console;

    // Constructors ----------------------------------------------------------------------------------------------------

    /**
     * Simply assigns references, must not throw exception. The initialization work that may throw exceptions must
     * be performed in init():
     */
    public Context(HttpServletRequest request, HttpServletResponse response) {

        this.request = request;
        this.response = response;
        this.console = new Console();
    }

    // Public ----------------------------------------------------------------------------------------------------------

    public void init() throws HttpException {

        lookUpSession();
    }

    /**
     * An instance to use to send info/warn/error messages to the client. They will be rendered appropriately in
     * HTML, and also logged into the server log. The messages should be readable text to be displayed on the client to
     * inform the user on the outcome of a command or other events.
     */
    public Console getConsole() {

        return console;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    /**
     * May return null, if no HTTP session was found.
     */
    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

    /**
     * Must not throw exception. If it cannot figure out the executing host name, return null and log an exception
     * that will help identifying the cause of failure.
     */
    public String getExecutingHost() {

        String name = null;

        try {

            name = queryJmxBus("jboss.system:type=ServerInfo", "HostName");
        }
        catch(Exception e)
        {
            log.error(e.getMessage());
        }

        // try one more time - this for tomcat

        if (name == null)
        {
            try
            {
                InetAddress addr = InetAddress.getLocalHost();
                return addr.getHostName();
            }
            catch(Exception e)
            {
                log.error("failed to get local host", e);
            }
        }

        return name;
    }

    private String queryJmxBus(String objectName, String attributeName)
    {
        MBeanServer jbossMBeanServer = getJBossMBeanServer();

        if (jbossMBeanServer == null)
        {
            log.debug("JBoss MBeanServer not found");
            return null;
        }

        try
        {
            Object o = jbossMBeanServer.getAttribute(new ObjectName(objectName), attributeName);

            if (o == null)
            {
                return null;
            }

            return o.toString();
        }
        catch(Exception e)
        {
            log.error("failed to query the JMX bus", e);
        }

        return null;
    }

    /**
     * @return null if the JBoss MBeanServer not found.
     */
    private MBeanServer getJBossMBeanServer()
    {
        ArrayList<MBeanServer> servers = MBeanServerFactory.findMBeanServer(null);

        for(MBeanServer s: servers)
        {
            if ("jboss".equals(s.getDefaultDomain()))
            {
                return s;
            }
        }

        return null;
    }

    /**
     * May return null;
     */
    public String getRemoteUser() {

        return request.getRemoteUser();
    }

    /**
     * May return null;
     */
    public String getRemoteAddress() {

        return request.getRemoteAddr();
    }

    /**
     * May return null;
     */
    public String getRemoteHost() {

        return request.getRemoteHost();
    }

    /**
     * @return may return an empty list, but never null.
     */
    public List<Cookie> getCookies() {

        Cookie[] cookies = request.getCookies();

        if (cookies == null || cookies.length == 0) {
            return Collections.emptyList();
        }

        return Arrays.asList(cookies);
    }

    /**
     * @return null if no such cookie exists
     */
    public Cookie getCookie(String name) {

        Cookie[] cookies = request.getCookies();

        if (cookies == null || cookies.length == 0) {
            return null;
        }

        for(Cookie c: cookies) {
            if (c.getName().equals(name)) {
                return c;
            }
        }

        return null;
    }

    /**
     * @return null if no such cookie exists
     */
    public String getCookieValue(String name) {

        Cookie c = getCookie(name);

        if (c == null) {
            return null;
        }

        return c.getValue();
    }

    /**
     * @return all request headers present in the request. May return an empty list but never null.
     */
    public List<Header> getRequestHeaders() {

        List<Header> result = new ArrayList<>();

        for(Enumeration e = request.getHeaderNames(); e.hasMoreElements(); ) {
            String headerName = (String)e.nextElement();
            result.add(new Header(headerName, request.getHeader(headerName)));
        }

        return result;
    }

    /**
     * @return all response headers present in the request. May return an empty list but never null.
     */
    public List<Header> getResponseHeaders() {

        List<Header> result = new ArrayList<>();

        for(String headerName: response.getHeaderNames()) {
            result.add(new Header(headerName, request.getHeader(headerName)));
        }

        return result;
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    private void lookUpSession() throws HttpException {

        String jsessionId = getCookieValue("JSESSIONID");

        session = request.getSession(false);

        if (jsessionId != null && session == null) {
            throw new HttpException(400, "the browser sends an obsolete JSESSIONID: " + jsessionId + ", try clearing the cookies");
        }

        if (session != null) {

            if (jsessionId == null) {
                //
                // this shouldn't happen, we can't look up a session with a null session ID
                //
                throw new IllegalStateException("got a non-null session for a null JSESSIONID");
            }

            log.info("existing session acquired");
        }
    }

    // Inner classes ---------------------------------------------------------------------------------------------------

}
