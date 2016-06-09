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
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
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

    // Constructors ----------------------------------------------------------------------------------------------------

    public Context(HttpServletRequest request, HttpServletResponse response) {

        this.request = request;
        this.response = response;

    }

    // Public ----------------------------------------------------------------------------------------------------------

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
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

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
