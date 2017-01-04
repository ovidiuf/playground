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
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * The instance that manages connections - provides query functionality.
 *
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/4/17
 */
public class ConnectionManager {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(ConnectionManager.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private AtomicLong connectionIdGenerator;

    // Connections keyed by ID
    private Map<Long, Connection> connections;

    // Constructors ----------------------------------------------------------------------------------------------------

    public ConnectionManager() {

        connectionIdGenerator = new AtomicLong(-1);
        connections = new ConcurrentHashMap<>();
    }

    // Public ----------------------------------------------------------------------------------------------------------

    /**
     * Must be thread-safe.
     *
     * @exception java.io.IOException if we fail due to an I/O problem during the creation process.
     */
    public Connection buildConnection(Socket socket) throws IOException {

        Connection c = new Connection(connectionIdGenerator.incrementAndGet(), socket, this);
        connections.put(c.getId(), c);

        log.info(c + " created and registered");

        return c;
    }

    @SuppressWarnings("unused")
    public int getConnectionCount() {

        return connections.size();
    }

    // Package protected -----------------------------------------------------------------------------------------------

    void remove(Connection c) {

        Connection c2 = connections.remove(c.getId());

        if (c2 != null) {

            log.info(c2 + " removed");
        }
    }

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
