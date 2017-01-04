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

package io.novaordis.playground.http.server;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ServerSocketFactory;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

/**
 * The top level server super-structure. This instance is responsible with the management of the main acceptor
 * thread, which should accept connections and hand them out as quickly as possible to other threads for processing.
 *
 * The server starts to listen as soon as the listen() method is invoked: the method releases the main acceptor thread,
 * which starts accepting HTTP connections. The acceptor thread is a non-daemon thread, so it will keep the JVM up even
 * if the thread that started the server (presumably main) exists.
 *
 * The server can be shut down by invoking a shutdown URL (usually http://<host>:<port>/exit). The actual exit URL path
 * is declared as EXIT_URL_PATH.
 *
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/4/17
 */
public class Server {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(Server.class);

    public static final int DEFAULT_BACKLOG = 10;

    public static final String EXIT_URL_PATH = "/exit";

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private int port;
    private int backlog;
    private ServerSocket serverSocket;
    private CountDownLatch listenLatch;
    private volatile boolean listening;

    // Constructors ----------------------------------------------------------------------------------------------------

    /**
     * Binds the server socket and performs all required steps to ready itself, short of actual accepting (listening).
     *
     * @param port the local port to listen on for HTTP connections
     *
     * @exception IOException on any problem related to server socket creation or binding.
     */
    public Server(int port) throws IOException, InterruptedException {

        this.port = port;
        this.backlog = DEFAULT_BACKLOG;

        SocketAddress endpoint = new InetSocketAddress(port);
        this.serverSocket = ServerSocketFactory.getDefault().createServerSocket();
        this.serverSocket.bind(endpoint, backlog);

        final AtomicLong requestCounter = new AtomicLong(0);
        listenLatch = new CountDownLatch(1);

        String acceptorThreadName = "HTTP Server " + port + " Acceptor Thread";

        Thread acceptorThread = new Thread(() -> {

            //
            // wait for the listen() method to be called to actually start listening. If this thread is forcibly
            // interrupted while waiting on latch, log the error and abort
            //
            try {

                listenLatch.await();
                listening = true;
            }
            catch(InterruptedException e) {

                log.error(Thread.currentThread().getName() + " interrupted before it started listening, aborting ...");
                return;
            }

            while(listening) {

                try {

                    Socket s = serverSocket.accept();

                    log.debug("new connection accepted");

                    if (!listening) {

                        //
                        // ignore anything that came after the listening flag was flipped
                        //

                        log.debug("http server not listening anymore, exiting ...");
                        return;
                    }

                    new Thread(new RequestHandler(requestCounter.get(), this, s),
                            "HTTP Request Handler Thread " + requestCounter.get()).start();

                    requestCounter.incrementAndGet();
                }
                catch (IOException e) {

                    log.error("server socket accept failed", e);
                }
            }


        }, acceptorThreadName);
        acceptorThread.setDaemon(false);
        acceptorThread.start();

        log.info("http server bound to " + port);
    }

    // Public ----------------------------------------------------------------------------------------------------------

    /**
     * The method releases the main acceptor thread, which starts listening (accepting) for HTTP connections. The
     * acceptor thread is a non-daemon thread, so it will keep the JVM up even if the thread that started the server
     * (presumably main) exists.
     */
    public void listen() {

        listenLatch.countDown();
    }

    @SuppressWarnings("unused")
    public int getBacklog() {

        return backlog;
    }

    @Override
    public String toString() {

        return "http server " + port;
    }

    // Package protected -----------------------------------------------------------------------------------------------

    /**
     * This method should be used by the request processing threads to message the server instance that they got the
     * exit request.
     */
    void exit() {

        log.debug("http server requested to exit");

        listening = false;

        //
        // register a daemon timer that will open a connection to the server socket after a while, to un-block it in
        // case it managed to get blocked in accept() before the flag flipped. It is important to declare the timer
        // a daemon, because otherwise it'll hang the JVM
        //

        new Timer(true).schedule(new TimerTask() {
            @Override
            public void run() {

                try {

                    log.debug("shutdown task running ...");
                    Socket s = new Socket();
                    s.connect(serverSocket.getLocalSocketAddress());
                    s.close();
                }
                catch(Exception e) {

                    log.error("failed to operate the shutdown socket", e);
                }
            }
        }, 100L);
    }

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
