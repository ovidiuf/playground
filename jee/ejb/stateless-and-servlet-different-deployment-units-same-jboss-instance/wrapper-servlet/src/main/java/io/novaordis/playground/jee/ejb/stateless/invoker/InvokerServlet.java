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

package io.novaordis.playground.jee.ejb.stateless.invoker;

import io.novaordis.playground.jee.ejb.stateless.SimpleStateless;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
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
public class InvokerServlet extends HttpServlet {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(InvokerServlet.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    //
    // Equivalent @EJB injection
    //
    //@EJB(mappedName = "java:global/stateless-ejb-example/SimpleStatelessBean")
    //private SimpleStateless bean;

    // Constructors ----------------------------------------------------------------------------------------------------

    // HttpServlet overrides -------------------------------------------------------------------------------------------

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {

        SimpleStateless bean;

        InitialContext ic = null;

        try {

            ic = new InitialContext();
            bean = (SimpleStateless)ic.lookup("java:global/stateless-ejb-example/SimpleStatelessBean");
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

        String result = bean.methodOne("from servlet");

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        out.println("response from EJB: " + result);
    }

    // Public ----------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return "InvokerServlet[" + Integer.toHexString(System.identityHashCode(this)) + "]";
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
