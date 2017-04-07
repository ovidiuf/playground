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

package io.novaordis.playground.jee.ear.servlet;

import io.novaordis.playground.jee.ear.ejb.A;
import io.novaordis.playground.jee.ear.ejb.B;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 3/22/17
 */
public class ServletA extends HttpServlet {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(ServletA.class);

    // Static ----------------------------------------------------------------------------------------------------------

    private static void dumpContext(String contextName) throws IOException {

        try {

            InitialContext ic = new InitialContext();

            Context c = (Context)ic.lookup(contextName);

            NamingEnumeration<Binding>  be = c.listBindings("/");

            String s = "\n\n" + contextName + ":\n";

            while(be.hasMore()) {

                Binding b = be.next();
                s += "        " + b.getName() + " -> " + b.getObject() + " (" + b.getClassName() + ")\n";
            }

            log.info(s);

            ic.close();
        }
        catch (Exception e) {

            throw new IOException(e);
        }
    }

    private static void dumpJndiBinding(String name) throws IOException {

        try {

            InitialContext ic = new InitialContext();

            Object o = ic.lookup(name);
            String s = "\n\n" + name + ": " + o + "\n";
            log.info(s);

            ic.close();
        }
        catch (Exception e) {

            throw new IOException(e);
        }
    }

    // Attributes ------------------------------------------------------------------------------------------------------

    @Resource
    private int configurationParameterA;

    // Constructors ----------------------------------------------------------------------------------------------------

    // HttpServlet overrides -------------------------------------------------------------------------------------------

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {

        // displayConfigurationParameterA(res);
        displayDeploymentDescriptorDeclaredConfigurationParameterB(res);
    }

    // Public ----------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return "InvokerServlet[" + Integer.toHexString(System.identityHashCode(this)) + "]";
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    private void displayConfigurationParameterA(HttpServletResponse res) throws IOException {

        String r = "" + configurationParameterA;
        sendResponse(r, res);
    }

    private void displayDeploymentDescriptorDeclaredConfigurationParameterB(HttpServletResponse res)
            throws IOException {

        try {

            InitialContext c = new InitialContext();

            Integer inComp = (Integer)c.lookup("java:comp/env/configuration-parameter-B");
            Integer inModule = (Integer)c.lookup("java:module/env/configuration-parameter-B");
            Integer inApp = (Integer)c.lookup("java:app/env/configuration-parameter-B");

            log.info("\n\nin comp: " + inComp + "\nin module: " + inModule + "\n");

            c.close();
        }
        catch(Exception e) {

            throw new IOException(e);
        }
    }


    private void todo() throws ServletException {

        A beanA;
        B beanB;

        InitialContext ic = null;

        try {

            ic = new InitialContext();

            beanA = (A)ic.lookup("java:app/ejbs/ABean");
            beanB = (B)ic.lookup("java:app/ejbs/BBean");

        }
        catch(Exception e) {

            throw new ServletException(e);
        }
        finally {

            if (ic != null) {

                try {
                    ic.close();
                }
                catch(Exception e) {

                    log.error("failed to close initial context", e);
                }
            }
        }

        String result = beanA.methodOne("from servlet");
        String result2 = beanB.methodTwo("from servlet");


    }

    private void sendResponse(String response, HttpServletResponse res) throws IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        out.println(response);
        out.flush();
    }

    // Inner classes ---------------------------------------------------------------------------------------------------

}
