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

package io.novaordis.playground.http.server.jmx;

import io.novaordis.playground.http.server.ServerImpl;
import io.novaordis.playground.http.server.connection.Connection;
import io.novaordis.playground.http.server.connection.ConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/7/17
 */
public class ManagementConsole implements ManagementConsoleMBean {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(ManagementConsole.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private ServerImpl server;

    // Constructors ----------------------------------------------------------------------------------------------------

    public ManagementConsole(ServerImpl server) {

        this.server = server;
    }

    // ManagementConsoleMBean implementation ---------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    /**
     * Part of the JMX interface, will be queried by the JMX agent.
     */
    @Override
    @SuppressWarnings("unused")
    public int getConnectionCount() {

        return server.getConnectionManager().getConnectionCount();
    }

    // Methods ---------------------------------------------------------------------------------------------------------

    @Override
    public void listConnections() {

        ConnectionManager cm = server.getConnectionManager();

        //
        // live connections
        //

        List<Connection> ageOrderedConnections = new ArrayList<>(cm.getConnections());
        Collections.sort(ageOrderedConnections);
        String s = connectionListToString(true, ageOrderedConnections);
        log.info(s);
    }

    @Override
    public void listClosedConnections() {

        ConnectionManager cm = server.getConnectionManager();

        //
        // closed connections; the method returns an already ordered copy
        //

        List<Connection> closedConnections = cm.getClosedConnections();
        String s = connectionListToString(false, closedConnections);
        log.info(s);
    }

    @Override
    public void clearClosedConnectionHistory() {

        ConnectionManager cm = server.getConnectionManager();
        cm.clearClosedConnectionHistory();
    }

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    /**
     * @param alive true if the connections in the list are live connections, false if they are closed connections.
     */
    String connectionListToString(boolean alive, List<Connection> connections) {

        if (connections.isEmpty()) {

            return "no " + (alive ? "live" : "closed") + " connections";
        }

        String s = (alive ? "live" : "closed") + " connections:\n";

        for(Iterator<Connection> i = connections.iterator(); i.hasNext(); ) {

            Connection c = i.next();

            s += "connection " + c.getId() + " " + c.getConnectionInfo();

            if (i.hasNext()) {

                s += "\n";
            }
        }

        return s;
    }

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
