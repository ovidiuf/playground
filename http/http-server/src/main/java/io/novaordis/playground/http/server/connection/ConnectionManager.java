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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * The instance that manages connections - provides query functionality.
 *
 * Must be thread safe.
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
    private Map<Long, Connection> aliveConnections;

    private boolean persistentConnections;

    //
    // maintained in the order in which they were closed - for reporting purposes. Note that this may grow large
    // after a while TODO find a solution to the memory problem
    //
    final private List<Connection> closedConnections;

    // Constructors ----------------------------------------------------------------------------------------------------

    /**
     * @param persistentConnections says whether this manager builds persistent connections or not
     */
    public ConnectionManager(boolean persistentConnections) {

        this.persistentConnections = persistentConnections;
        connectionIdGenerator = new AtomicLong(-1);
        aliveConnections = new ConcurrentHashMap<>();
        closedConnections = new ArrayList<>();
    }

    // Public ----------------------------------------------------------------------------------------------------------

    /**
     * Must be thread-safe.
     *
     * @exception java.io.IOException if we fail due to an I/O problem during the creation process.
     */
    public Connection buildConnection(Socket socket) throws IOException {

        Connection c = new Connection(connectionIdGenerator.incrementAndGet(), socket, persistentConnections, this);
        aliveConnections.put(c.getId(), c);

        log.info(c + " created and registered");

        return c;
    }

    public int getConnectionCount() {

        return aliveConnections.size();
    }

    /**
     * @return a copy of the snapshot of the alive connection map.
     */
    public Collection<Connection> getConnections() {

        return new HashSet<>(aliveConnections.values());
    }

    /**
     * @return a copy fo the list of closed connection, in the order in which they were closed.
     */
    public List<Connection> getClosedConnections() {

        synchronized (closedConnections) {

            return new ArrayList<>(closedConnections);
        }
    }

    public void clearClosedConnectionHistory() {

        synchronized (closedConnections) {

            closedConnections.clear();

            log.info("closed connection history cleared");
        }
    }

    // Package protected -----------------------------------------------------------------------------------------------

    void remove(Connection c) {

        Connection c2 = aliveConnections.remove(c.getId());

        if (c2 != null) {

            log.info(c2 + " removed");

            synchronized (closedConnections) {

                closedConnections.add(c2);
            }
        }
    }

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
