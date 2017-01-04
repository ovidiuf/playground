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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Handles a single HTTP request on a dedicated thread. Each request gets its own thread.
 *
 * The handler is responsible for closing the socket after the response has been sent back.
 *
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/4/17
 */
public class RequestHandler implements Runnable {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private long requestID;
    private Socket socket;
    private Server server;

    // Constructors ----------------------------------------------------------------------------------------------------

    /**
     * @param server reference to use to invoke the exit() method on, in case the exit request was sent into the server.
     */
    public RequestHandler(long requestID, Server server, Socket s) {

        this.requestID = requestID;
        this.server = server;
        this.socket = s;
    }

    // Runnable implementation -----------------------------------------------------------------------------------------

    @Override
    public void run() {

        try {

            HttpRequest request = readRequest();

            if (request == null) {

                //
                // failed to read request, error was logged
                //

                sendResponse(HttpStatusCode.BAD_REQUEST);
            }
            else {

                log.info(request.toString());

                sendResponse(HttpStatusCode.OK);

                if (Server.EXIT_URL_PATH.equals(request.getPath())) {

                    server.exit();
                }
            }
        }
        finally {

            try {

                socket.close();
            }
            catch(IOException e) {

                log.error("failed to close socket", e);
            }
        }
    }

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    /**
     * @return null if it fails to read request for some reason. The error is logged.
     */
    HttpRequest readRequest() {

        try {

            InputStream is = socket.getInputStream();
            ByteArrayOutputStream header = new ByteArrayOutputStream();

            //
            // read up to the blank line
            //

            boolean cr = false;

            while(true) {

                int i = is.read();

                if (-1 == i) {

                    //
                    // EOS encountered before fully reading the header
                    //

                    throw new RuntimeException("NOT YET IMPLEMENTED: EOS encountered before fully reading the header");
                }

                if ('\r' == (char)i) {

                    cr = true;
                    continue;
                }

                if ('\n' == (char)i) {

                    if (cr) {

                        //
                        // blank line encountered
                        //

                        break;
                    }

                    throw new RuntimeException("NOT YET IMPLEMENTED: LF received without a preceding CR");
                }

                header.write(i);
            }


            return new HttpRequest(header.toByteArray());

        }
        catch (Exception e) {

            log.error("failed to read request", e);
            return null;
        }
    }

    void sendResponse(HttpStatusCode statusCode) {

        try {

            OutputStream os = socket.getOutputStream();

            String statusLine = "HTTP/1.1 " + statusCode.getStatusCode() + " " + statusCode.getReasonPhrase();
            String response = statusLine + "\r\n" + "\r\n";

            os.write(response.getBytes());
            os.close();

        }
        catch(Exception e) {

            log.error("failed to write response", e);
        }
    }

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
