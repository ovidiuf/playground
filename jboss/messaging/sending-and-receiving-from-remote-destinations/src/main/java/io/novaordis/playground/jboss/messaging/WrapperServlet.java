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
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/9/16
 */
public class WrapperServlet extends HttpServlet {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(WrapperServlet.class);

    public static final String LOCAL_NAME_OF_THE_EXTERNAL_CONTEXT = "java:global/remote-hornetq";

    public static final String DESTINATION_JNDI_NAME = "jms/queue/remote-playground";
    // public static final String DESTINATION_JNDI_NAME = "jms/queue/remote-inbound";

    public static final String CONNECTION_FACTORY_JNDI_NAME = "java:/RemoteJmsXA";

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    @Resource(lookup = "java:global/remote-hornetq")
    private InitialContext externalContext;

    private InitialContext localInitialContext;

    // Constructors ----------------------------------------------------------------------------------------------------

    public WrapperServlet() throws Exception {

        localInitialContext = new InitialContext();
    }

    // HttpServlet overrides -------------------------------------------------------------------------------------------

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        log.info(this + " handling GET");

        String uri = req.getRequestURI();

        if (uri.startsWith("/wrapper-servlet/send")) {

            //send();
            //send2();
            //sendMultipleMessagesOnDifferentConnectionsOnTheSameThread();
            sendMultipleMessagesOnDifferentConnectionsOnDifferentThreads();
        }
        else if (uri.startsWith("/wrapper-servlet/receive")) {

            //receive();
            receive2();
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

            Queue queue = (Queue)ic.lookup(LOCAL_NAME_OF_THE_EXTERNAL_CONTEXT + "/" + DESTINATION_JNDI_NAME);

            log.info("queue: " + queue);

            ConnectionFactory cf = (ConnectionFactory)ic.lookup(CONNECTION_FACTORY_JNDI_NAME);

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

    /**
     * Equivalent with send(), just that we do not create the external InitialContext ourselves, we use the one that was
     * injected.
     */
    private void send2() throws ServletException {

        Connection c = null;

        try {

            Queue queue = (Queue)externalContext.lookup(DESTINATION_JNDI_NAME);

            log.info("queue: " + queue);

            ConnectionFactory cf = (ConnectionFactory)localInitialContext.lookup(CONNECTION_FACTORY_JNDI_NAME);

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
        }
    }

    private void sendMultipleMessagesOnDifferentConnectionsOnTheSameThread() throws ServletException {

        Connection c = null;

        int messageCount = 100;

        try {

            Queue queue = (Queue)externalContext.lookup(DESTINATION_JNDI_NAME);

            log.info("queue: " + queue);

            ConnectionFactory cf = (ConnectionFactory)localInitialContext.lookup(CONNECTION_FACTORY_JNDI_NAME);

            log.info("connection factory: " + cf);

            for(int i = 0; i < messageCount; i ++) {

                c = cf.createConnection();

                Session s = c.createSession(false, Session.AUTO_ACKNOWLEDGE);

                MessageProducer p = s.createProducer(queue);

                String text = "test " + UUID.randomUUID().toString();

                TextMessage tm = s.createTextMessage(text);

                c.start();

                p.send(tm);

                log.info("sent message \"" + text + "\"");

                c.close();

                Thread.sleep(100L);
            }
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

    private void sendMultipleMessagesOnDifferentConnectionsOnDifferentThreads() throws ServletException {

        int messageCount = 100;

        final CountDownLatch latch = new CountDownLatch(3);

        try {

            Queue queue = (Queue) externalContext.lookup(DESTINATION_JNDI_NAME);

            log.info("queue: " + queue);

            ConnectionFactory cf = (ConnectionFactory) localInitialContext.lookup(CONNECTION_FACTORY_JNDI_NAME);

            log.info("connection factory: " + cf);

            for (int i = 0; i < messageCount; i++) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {

                            Connection c = cf.createConnection();

                            Session s = c.createSession(false, Session.AUTO_ACKNOWLEDGE);

                            MessageProducer p = s.createProducer(queue);

                            String text = "test " + UUID.randomUUID().toString();

                            TextMessage tm = s.createTextMessage(text);

                            c.start();

                            p.send(tm);

                            log.info("sent message \"" + text + "\" with connection " + c + " on thread " + Thread.currentThread().getName() );

                            //
                            // wait until the other threads reached the same point - this way we are sure we are using
                            // connections in parallel
                            //
                            latch.countDown();
                            latch.await();

                            c.close();
                        }
                        catch (Exception e) {

                            throw new IllegalArgumentException(e);
                        }
                    }
                }, "Sending Thread #" + i).start();
            }
        }
        catch(Exception e) {

            throw new ServletException(e);
        }
    }

    private void receive() throws ServletException {

        InitialContext ic = null;
        Connection c = null;

        try {

            ic = new InitialContext();

            Queue queue = (Queue)ic.lookup(LOCAL_NAME_OF_THE_EXTERNAL_CONTEXT + "/" + DESTINATION_JNDI_NAME);

            log.info("queue: " + queue);

            ConnectionFactory cf = (ConnectionFactory)ic.lookup(CONNECTION_FACTORY_JNDI_NAME);

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

    /**
     * Equivalent with receive(), just that we do not create the external InitialContext ourselves, we use the one that
     * was injected.
     */
    private void receive2() throws ServletException {

        Connection c = null;

        try {

            Queue queue = (Queue)externalContext.lookup(DESTINATION_JNDI_NAME);

            log.info("queue: " + queue);

            ConnectionFactory cf = (ConnectionFactory)localInitialContext.lookup(CONNECTION_FACTORY_JNDI_NAME);

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
        }
    }

    // Inner classes ---------------------------------------------------------------------------------------------------

}
