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

package io.novaordis.playground.jboss.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Service;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.util.UUID;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/9/16
 */
public class WrapperServlet extends HttpServlet {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(WrapperServlet.class);

    public static final String DESTINATION_JNDI_NAME = "/jms/queue/remote-playground";

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // HttpServlet overrides -------------------------------------------------------------------------------------------

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        log.info(this + " handling GET");

        String uri = req.getRequestURI();

        if (uri.startsWith("/wrapper-servlet/send")) {

            send();
        }
        else if (uri.startsWith("/wrapper-servlet/receive")) {

            receive();
        }
        else {

            throw new ServletException("don't know how to handle " + uri);
        }

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        out.println("ok");
    }

    // Public ----------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {

        return "WrapperServlet[" + Integer.toHexString(System.identityHashCode(this)) + "]";
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    private void send() throws ServletException {

        InitialContext ic = null;
        Connection c = null;

        try {

            ic = new InitialContext();

            Object o = ic.lookup("java:global/remote-hornetq" + DESTINATION_JNDI_NAME);

            Queue queue = (Queue)o;

            log.info("queue: " + queue);

            ConnectionFactory cf = (ConnectionFactory)ic.lookup("java:/RemoteJmsXA");

            log.info("connection factory: " + cf);

            c = cf.createConnection();

            Session s = c.createSession(false, Session.AUTO_ACKNOWLEDGE);

            MessageProducer p = s.createProducer(queue);

            String text = "test " + UUID.randomUUID().toString();

            TextMessage tm = s.createTextMessage(text);

            c.start();

            p.send(tm);

            log.info("sent message \"" + text + "\"");
        }
        catch(Exception e) {

            throw new ServletException(e);
        }
        finally {

            if (c != null) {

                try {

                    c.close();
                }
                catch(Exception e) {

                    log.error("failed to close JMS connection", e);
                }
            }

            if (ic != null) {

                try {

                    ic.close();
                }
                catch(Exception e) {

                    log.error("failed to close initial context", e);
                }
            }
        }
    }

    private void receive() throws ServletException {

        InitialContext ic = null;
        Connection c = null;

        try {

            ic = new InitialContext();

            Object o = ic.lookup("java:global/remote-hornetq" + DESTINATION_JNDI_NAME);

            Queue queue = (Queue)o;

            log.info("queue: " + queue);

            ConnectionFactory cf = (ConnectionFactory)ic.lookup("java:/RemoteJmsXA");

            log.info("connection factory: " + cf);

            c = cf.createConnection();

            Session s = c.createSession(false, Session.AUTO_ACKNOWLEDGE);

            MessageConsumer mc = s.createConsumer(queue);

            c.start();

            log.info("blocking to receive message ...");

            TextMessage tm = (TextMessage)mc.receive();

            log.info("received \"" + tm.getText() + "\"");
        }
        catch(Exception e) {

            throw new ServletException(e);
        }
        finally {

            if (c != null) {

                try {

                    c.close();
                }
                catch(Exception e) {

                    log.error("failed to close JMS connection", e);
                }
            }

            if (ic != null) {

                try {

                    ic.close();
                }
                catch(Exception e) {

                    log.error("failed to close initial context", e);
                }
            }
        }

    }


    // Inner classes ---------------------------------------------------------------------------------------------------

}
