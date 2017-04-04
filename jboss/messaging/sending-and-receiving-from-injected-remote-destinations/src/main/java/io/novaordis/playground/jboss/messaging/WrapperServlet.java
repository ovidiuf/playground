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

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/9/16
 */
public class WrapperServlet extends HttpServlet {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(WrapperServlet.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    @Resource(name="java:global/remote-hornetq/jms/queue/remote-inbound")
    private Queue remoteQueue;

    @Resource(name="java:/RemoteJmsXA")
    private ConnectionFactory connectionFactory;

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

        Connection c = null;

        try {

            log.info("queue: " + remoteQueue);
            log.info("connection factory: " + connectionFactory);

            c = connectionFactory.createConnection();

            Session s = c.createSession(false, Session.AUTO_ACKNOWLEDGE);

            MessageProducer p = s.createProducer(remoteQueue);

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
        }
    }

    private void receive() throws ServletException {

        Connection c = null;

        try {

            log.info("queue: " + remoteQueue);
            log.info("connection factory: " + connectionFactory);

            c = connectionFactory.createConnection();

            Session s = c.createSession(false, Session.AUTO_ACKNOWLEDGE);

            MessageConsumer mc = s.createConsumer(remoteQueue);

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
        }
    }

    // Inner classes ---------------------------------------------------------------------------------------------------

}
