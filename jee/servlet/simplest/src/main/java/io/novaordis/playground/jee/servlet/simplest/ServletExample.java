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

package io.novaordis.playground.jee.servlet.simplest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/9/16
 */
public class ServletExample extends HttpServlet {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(ServletExample.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private static String version = "2.0";

    // Constructors ----------------------------------------------------------------------------------------------------

    // HttpServlet overrides -------------------------------------------------------------------------------------------

    @Override
    public void init() {
        log.info(this + " initialized");
    }

    @Override
    public void destroy() {
        log.info(this + " destroyed");
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        log.info(this + " handling GET");

        res.setContentType("text/html");

        PrintWriter out = res.getWriter();

        out.println("<html>");
        out.println("GET handled by " + getHostName() + ", version " + version);
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

    /**
     * May return null.
     */
    private String getHostName() {

        log.info("getHostName()");

        String hostName = System.getenv("HOSTNAME");

        if (hostName == null || hostName.length() == 0) {

            String command = "/bin/hostname";

            Process p = null;
            StringBuilder sb = null;

            try {

                p = Runtime.getRuntime().exec(new String[]{command});

                log.info(p + " created");

                InputStream is = p.getInputStream();
                sb = new StringBuilder();
                int i;
                while ((i = is.read()) != -1) {
                    sb.append((char)i);
                }
            }
            catch (Exception e) {

                log.error("failed to execute \"" + command + "\"");
            }
            finally {

                if (p != null) {
                    p.destroy();
                }
            }

            log.info("sb: " + sb);

            if (sb != null) {
                hostName = sb.toString();
            }
        }

        return hostName;
    }

    // Inner classes ---------------------------------------------------------------------------------------------------

}
