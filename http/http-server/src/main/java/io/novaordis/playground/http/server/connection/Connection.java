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

package io.novaordis.playground.http.server.connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

/**
 * The abstraction used to model a browser - server connection, including the socket and the underlying TCP/IP
 * connection.
 *
 * If the browser keeps the connections (and implicitly the underlying socket) alive, the server will keep re-using
 * the same Connection instance.
 *
 * The implementation is not required to be thread-safe: a connection instance will be handled by only one thread
 * at a time.
 *
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/4/17
 */
public class Connection {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(Connection.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private long id;

    // our manager
    private ConnectionManager manager;

    private Socket socket;

    private boolean persistent;

    private volatile boolean isClosing;

    private String userAgent;

    // Constructors ----------------------------------------------------------------------------------------------------

    /**
     * Instances built only by the ConnectionManager (or subclasses or other classes of this package).
     *
     * @param socket the underlying socket
     * @param persistent says whether this connection is persistent or not
     *
     * @exception java.io.IOException if we fail due to an I/O problem during the creation process.
     */
    protected Connection(long id, Socket socket, boolean persistent, ConnectionManager manager)
            throws IOException {

        this.id = id;
        this.manager = manager;
        this.socket = socket;
        this.persistent = persistent;
    }

    // Public ----------------------------------------------------------------------------------------------------------

    public long getId() {

        return id;
    }

    /**
    * @return true if the connection handler enforces persistent connections. This is the default in HTTP/1.1
    */
    public boolean isPersistent() {

        return persistent;
    }

    public void setPersistent(boolean b) {

        this.persistent = b;
    }

    /**
     * Reads the next byte of data from the underlying socket input stream. The value byte is returned as an int in the
     * range 0 to 255. If no byte is available because the end of the underlying socket stream has been reached, the
     * value -1 is returned. This method blocks until input data is available, the end of the stream is detected, or an
     * exception is thrown.
     *
     * @return     the next byte of data, or -1 if the end of the underlying socket stream is reached.
     * @exception ConnectionException  if an error occurs. ConnectionException should only be used to indicate
     * Connection bad quality or instability. Once a ConnectionException has been triggered by a Connection instance,
     * the instance should be generally considered unreliable and closed as soon as possible. getCause() will return
     * the underlying cause.
     */
    public int read() throws ConnectionException {

        try {

            return socket.getInputStream().read();
        }
        catch(SocketException e) {

            if (isClosing) {

                //
                // when we're blocked in read, and the client has requested to close the connection (or we're
                // configured to close the connection) after a request, and socket.close() was called, we'll get a
                // "java.net.SocketException: Socket is closed" or similar here. We should not bubble this up, because
                // it is unnecessary alarming, we'll just debug log it.
                //

                log.debug(this + " threw " + e.getClass().getSimpleName() + ": \"" + e.getMessage() +
                        "\" but the connection is in process of being closed, so that is expected");

                return -1;
            }
            else {

                throw new ConnectionException(e);
            }
        }
        catch(IOException e) {

            throw new ConnectionException(e);
        }
    }

    /**
     * Writes b.length bytes from the specified byte array to the underlying socket output stream.
     *
     * @see OutputStream#write(byte[])
     *
     * @param      b   the data.
     * @exception ConnectionException  if an error occurs. ConnectionException should only be used to indicate
     * Connection bad quality or instability. Once a ConnectionException has been triggered by a Connection instance,
     * the instance should be generally considered unreliable and closed as soon as possible. getCause() will return
     * the underlying cause.
     */
    public void write(byte[] b) throws ConnectionException {

        try {

            socket.getOutputStream().write(b);
        }
        catch(IOException e) {

            throw new ConnectionException(e);
        }
    }

    /**
     * Flushes the underlying socket output stream and forces any buffered output bytes to be written out.
     *
     * @see OutputStream#flush()
     *
     * @exception ConnectionException  if an error occurs. ConnectionException should only be used to indicate
     * Connection bad quality or instability. Once a ConnectionException has been triggered by a Connection instance,
     * the instance should be generally considered unreliable and closed as soon as possible. getCause() will return
     * the underlying cause.
     */
    public void flush() throws ConnectionException {

        try {

            socket.getOutputStream().flush();
        }
        catch(IOException e) {

            throw new ConnectionException(e);
        }
    }

    /**
     * This closes the connection and also updates the connection manager state.
     *
     * Do not throw exception on failure, handle problems internally.
     */
    public void close() {

        //
        // attempt to close the underlying socket, do not throw Exception, just log on failure
        //

        isClosing = true;

        try {

            socket.close();

            log.info(this + " closed");
        }
        catch (IOException e) {

            log.error("failed to close the underlying socket for " + this, e);
        }

        //
        // remove it from the connection manager anyway
        //
        if (manager != null) {

            manager.remove(this);
        }
    }

    public boolean isClosed() {

        return socket.isClosed();
    }

    /**
     * @return a string containing additional connection information, such as socket remote address and port, local
     * address and port, User-Agent content etc..
     */
    public String getConnectionInfo() {

        String socketInfo;

        if (socket == null) {

            socketInfo = "null socket";
        }
        else {

            socketInfo =
                    socket.getRemoteSocketAddress() + " -> " + socket.getLocalAddress() + ":" + socket.getLocalPort();

        }

        return socketInfo;
    }

    /**
     * @return the content of the User-Agent headers for the requests processed by this connection. Normally, there
     * should be only one value. If more than one value is detected, the setUserAgent() implementation will warn, but
     * it will record the history. May return null if no User-Agent was seen so far.
     */
    public String getUserAgent() {

        return userAgent;
    }

    /**
     * The User-Agent is not supposed to change for subsequent requests on the connection. If it does, we warn and
     * we record the history.
     */
    public void setUserAgent(String s) {

        if (userAgent == null) {

            userAgent = s;
            return;
        }

        if (userAgent.equals(s)) {

            return;
        }

        log.warn("different User-Agent value detected on " + this);

        userAgent += ", " + s;
    }

    @Override
    public String toString() {

        return "connection " + getId();
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    protected Socket getSocket() {

        return socket;
    }

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
