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

package io.novaordis.playground.jee.resource.comp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.jms.Queue;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
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

    //
    // declared in the "naming" subsystem, as <bindings><simple name="java:global/simple-global-int-example" value="" type=""/>
    //
    @Resource(lookup = "java:global/simple-global-int-example")
    private int simpleGlobalIntEnvironmentEntry;

    //
    // declared in the web.xml deployment descriptor, available from the java:module context
    //
    @Resource(lookup = "java:module/env/simple-module-int-example")
    private int simpleModuleIntEnvironmentEntry;

    //
    // declared in the web.xml deployment descriptor
    //
    @Resource(name = "simple-module-int-example")
    private int simpleModuleIntEnvironmentEntry2;

    //
    // message destination reference
    //

    //
    // this one looks up the original expiry queue
    //
    @Resource(lookup = "java:/jms/queue/ExpiryQueue")
    private Queue queue;

    //
    // and this the mapped (but the same) expiry queue
    //
    @Resource(lookup = "java:/Mapped-Expiry-Queue")
    private Queue queue2;

    // Constructors ----------------------------------------------------------------------------------------------------

    // HttpServlet overrides -------------------------------------------------------------------------------------------

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        log.info("GET");

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        out.println("simpleGlobalIntEnvironmentEntry: " + simpleGlobalIntEnvironmentEntry +
                ", simpleModuleIntEnvironmentEntry: " + simpleModuleIntEnvironmentEntry +
                ", simpleModuleIntEnvironmentEntry2: " + simpleModuleIntEnvironmentEntry2 +
                ", queue: " + queue +
                ", queue2: " + queue2);

    }

    // Public ----------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return "ServletExample[" + Integer.toHexString(System.identityHashCode(this)) + "]";
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    public static void dumpContext(String name, Context c) throws Exception {

        String s = "\n" + name + ":\n";

        NamingEnumeration<NameClassPair> ne = c.list("/");

        while(ne.hasMore()) {

            NameClassPair ncp = ne.next();
            String n = ncp.getName();
            Object o = c.lookup(n);

            s += " " + n + " -> " + o + "\n";
        }

        System.out.print(s);
    }

    // Inner classes ---------------------------------------------------------------------------------------------------

}
