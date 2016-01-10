package com.novaordis.playground.jee.servlet.simplest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/9/16
 */
public class ServletExample extends HttpServlet {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(ServletExample.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // HttpServlet overrides -------------------------------------------------------------------------------------------

    @Override
    public void init()
    {
        log.info(this + " initialized");
    }

    @Override
    public void destroy()
    {
        log.info(this + " destroyed");
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        log.info(this + " handling GET");

        res.setContentType("text/html");

        PrintWriter out = res.getWriter();

        out.println("<html>");
        out.println("GET handled by " + getHostName());
        out.println("</html>");
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)  throws ServletException, IOException {

        log.info(this + " handling POST");

        res.setContentType("text/html");

        PrintWriter out = res.getWriter();

        out.println("<html>");
        out.println("POST handled by " + getHostName());
        out.println("</html>");
    }

    // Public ----------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return "ServletExample[" + Integer.toHexString(System.identityHashCode(this)) + "]";
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    private String getHostName() {

        return System.getenv("HOSTNAME");
    }

    // Inner classes ---------------------------------------------------------------------------------------------------

}
