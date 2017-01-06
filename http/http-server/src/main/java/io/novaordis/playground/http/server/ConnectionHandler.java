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

import io.novaordis.playground.http.server.connection.Connection;
import io.novaordis.playground.http.server.connection.ConnectionException;
import io.novaordis.playground.http.server.http.HttpRequest;
import io.novaordis.playground.http.server.http.HttpResponse;
import io.novaordis.playground.http.server.http.HttpStatusCode;
import io.novaordis.playground.http.server.http.InvalidHttpRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * Handles a Connection (essentially the content arriving from the underlying socket) by extracting HTTP requests
 * and sending HTTP responses, on a dedicated thread. This way, each connection is handled by one, dedicated thread.
 * If connections are created and destroyed for each request, then each request such generated gets its own thread.
 *
 * The connection handler's job is to turn request bytes into a HttpRequest instance and turn a HttpResponse into bytes
 * and send it back over the connection. It does not handle the request. That is the RequestHandler's job.
 *
 * The handler is responsible for closing the connection, if appropriate.
 *
 * @see RequestHandler
 *
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/4/17
 */
public class ConnectionHandler {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(ConnectionHandler.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private final Server server;
    private final Connection connection;
    private final CountDownLatch startHandlerLatch;
    private final RequestHandler requestHandler;

    private boolean closeConnectionAfterResponse;

    private volatile boolean active;

    // Constructors ----------------------------------------------------------------------------------------------------

    /**
     * @param server reference to use to invoke the exit() method on, in case the exit request was sent into the server.
     */
    public ConnectionHandler(Server server, Connection connection) {

        this.server = server;
        this.connection = connection;
        this.closeConnectionAfterResponse = false;
        this.requestHandler = new RequestHandler();

        this.startHandlerLatch = new CountDownLatch(1);

        String threadName = connection.toString() + " Handling Thread";
        Thread connectionHandlerThread = new Thread(() -> {

            //
            // wait for the handleRequests() method to be called to actually start processing requests. If this thread
            // is forcibly interrupted while waiting on latch, log the error and abort
            //
            try {

                startHandlerLatch.await();
                active = true;
            }
            catch(InterruptedException e) {

                log.error(Thread.currentThread().getName() +
                        " interrupted before it started handling requests, aborting ...");
                return;
            }

            while(active) {

                try {

                    processNextRequestResponsePair();
                }
                catch(ConnectionException e) {

                    //
                    // this is where we handle connection problems and close the connection
                    //

                    log.error("connection failure", e);
                    connection.close();
                }
            }

        }, threadName);
        connectionHandlerThread.setDaemon(true);
        connectionHandlerThread.start();
    }

    // Public ----------------------------------------------------------------------------------------------------------

    /**
     * Initiate request processing on the handler's own thread
     */
    public void handleRequests() {

        startHandlerLatch.countDown();
    }

    @Override
    public String toString() {

        return "handler for " + connection;
    }

    // Package protected -----------------------------------------------------------------------------------------------

    /**
     * The method does not close connection voluntarily, it lets the client enforce its own policy.
     *
     * @exception ConnectionException  if a connection-related failure occurs. ConnectionException are only used to
     * indicate Connection bad quality or instability. Once a ConnectionException has been triggered by a Connection
     * instance, the instance should be generally considered unreliable and closed as soon as possible.
     */
    void sendResponse(HttpResponse response) throws ConnectionException {

        HttpStatusCode code = response.getStatusCode();
        sendResponse(code);
    }

    /**
     * The method does not close connection voluntarily, it lets the client enforce its own policy.
     *
     * @exception ConnectionException  if a connection-related failure occurs. ConnectionException are only used to
     * indicate Connection bad quality or instability. Once a ConnectionException has been triggered by a Connection
     * instance, the instance should be generally considered unreliable and closed as soon as possible.
     */
    void sendResponse(HttpStatusCode statusCode) throws ConnectionException {

        String statusLine = "HTTP/1.1 " + statusCode.getStatusCode() + " " + statusCode.getReasonPhrase();
        String response = statusLine + "\r\n" + "\r\n";

        connection.write(response.getBytes());
        connection.flush();

        log.debug("response wrote on " + connection);

        //
        // close or do not close the connection depending on the configuration.
        //

        if (closeConnectionAfterResponse) {

            //
            // close our connection and stop our thread
            //
            active = false;
            connection.close();
        }
    }

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    /**
     * Processes a request response.
     *
     * @throws ConnectionException on connection troubles
     */
    private void processNextRequestResponsePair() throws ConnectionException {

        try {

            HttpRequest request = HttpRequest.readRequest(connection);

            log.info("\n" + HttpRequest.showRequest(request));

            HttpResponse response = requestHandler.processRequest(request);

            if (Server.EXIT_URL_PATH.equals(request.getPath())) {

                log.info("\"" + request.getPath() + "\" special URL identified, initiating server shutdown ...");

                sendResponse(HttpStatusCode.OK);
                active = false;
                server.exit();
            }
            else {

                sendResponse(response);
            }
        }
        catch (InvalidHttpRequestException e) {

            //
            // failed because we were not able to parse the data came on the wire, do not close connection,
            // most likely something is wrong with this request only.
            //

            log.error("failed to parse HTTP request", e);
            sendResponse(HttpStatusCode.BAD_REQUEST);
        }
    }

    // Inner classes ---------------------------------------------------------------------------------------------------

}
