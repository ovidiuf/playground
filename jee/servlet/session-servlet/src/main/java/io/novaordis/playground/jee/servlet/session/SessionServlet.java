package io.novaordis.playground.jee.servlet.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;

/**
 * @author <a href="mailto:ovidiu@novaordis.com">Ovidiu Feodorov</a>
 *
 * Copyright 2016 Nova Ordis LLC
 */
public class SessionServlet extends HttpServlet
{
    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(SessionServlet.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // HttpServlet overrides -------------------------------------------------------------------------------------------

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {

        String path = req.getPathInfo();
        log.info("GET " + path);

        //
        // session handling
        //

        // look up session, but DO NOT create it if it's not there
        HttpSession session = req.getSession(false);
        log.info("HTTP session " + session);

        // figure out whether we shall establish a session
        if (shallWeEstablishSession(req)) {

            if (session != null) {

                // session already established - we don't support this case yet
                throw new ServletException("HTTP session already established,  we don't currently re-process 'establish-session=true'");
            }

            // no session, create one
            session = req.getSession(true);
            log.info("created HTTP session " + session);
        }

        dumpHeaders(req);

        String operationLog = interactWithCache(session, req);

        String executingHost = getExecutingHost();
        String user = req.getRemoteUser();
        String remoteAddress = req.getRemoteAddr();
        String remoteHost = req.getRemoteHost();
        Cookie[] cookies = req.getCookies();

        sendResponse(resp, path, executingHost, user, remoteAddress, remoteHost, cookies, operationLog);
    }

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    /**
     * @param nodeName - can be null if the node name cannot be determined
     */
    private void sendResponse(HttpServletResponse r, String path, String nodeName, String user, String remoteAddress,
                              String remoteHost, Cookie[] cookies, String operationLog) throws IOException {

        r.setContentType("text/html");

        PrintWriter out = r.getWriter();

        out.println("<html>");
        out.println("<head><title>Synthetic Response</title></head>");
        out.println("<body>");
        out.println("request successfully processed<br>");
        out.println("<br>");
        out.println("<tt>");

        out.println("time:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                new Date() + "\n<br>");
        out.println("path:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                path + "\n<br>");
        out.println("executing host:&nbsp;&nbsp;&nbsp;" +
                nodeName + "\n<br>");
        out.println("remote user:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                user + "\n<br>");
        out.println("remote address:&nbsp;&nbsp;&nbsp;" +
                remoteAddress + "\n<br>");
        out.println("remote host:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                remoteHost + "\n<br>");
        out.println("received cookies:&nbsp;" +
                (cookies == null || cookies.length == 0 ? "no cookies" : "") + "<br>\n");
        if (cookies != null)
        {
            for(int i = 0; i < cookies.length; i ++)
            {
                out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                        i + ") " + cookieToString(cookies[i]) + "<br>\n");
            }
        }

        out.println("<br>\n");
        out.println("operation log:&nbsp;" + (operationLog == null ? "-" : operationLog) + "<br>\n");

        out.println("</tt>\n<br>");
        out.println("</body></html>");
    }

    private void dumpHeaders(HttpServletRequest r) {

        for(Enumeration e = r.getHeaderNames(); e.hasMoreElements(); ) {
            String headerName = (String)e.nextElement();
            log.info(headerName + "=" + r.getHeader(headerName));
        }
    }

    /**
     * @return null if the node name cannot be determined.
     */
    private String getExecutingHost() {

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

    private String cookieToString(Cookie c)
    {
        StringBuilder sb = new StringBuilder();

        sb.append(c.getName()).append("=").append(c.getValue());

        String path = c.getPath();

        sb.append(path == null ? " (no path)" : " " + path);

        return sb.toString();
    }

    private boolean shallWeEstablishSession(HttpServletRequest r) {

        String s = r.getParameter("establish-session");
        return s != null;
    }

    /**
     * @return a human-readable report ("A=B was stored in the session")
     */
    private String interactWithCache(HttpSession session, HttpServletRequest req) throws ServletException {

        String path = req.getPathInfo();
        String key = req.getParameter("key");
        String value = req.getParameter("value");

        if ("/put".equals(path)) {

            if (session == null) {
                throw new ServletException("no active session, cannot put, use /&establish-session");
            }

            if (key == null) {
                throw new ServletException("null key, use /put?key=...&value=...");
            }

            if (value == null) {
                throw new ServletException("null value, use /put?key=...&value=...");
            }

            session.setAttribute(key, value);

            return "\"" + key + "\"=\"" + value + "\" stored in session " + session.getId();
        }
        else if ("/get".equals(path)) {

            if (session == null) {
                throw new ServletException("no active session, cannot get, use /&establish-session");
            }

            if (key == null) {
                throw new ServletException("null key, use /get?key=...");
            }

            Object v = session.getAttribute(key);

            return "\"" + key + "\"=\"" + v + "\" retrieved from session " + session.getId();
        }

        return null;
    }

    // Inner classes ---------------------------------------------------------------------------------------------------
}
