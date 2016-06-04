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

package io.novaordis.playground.wildfly.hornetq.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

/**
 * We need a connection manager, because the connection can be recreated in case of failure - it is supposed to
 * be HA.
 *
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 5/16/16
 */
public class ConnectionManager {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(ConnectionManager.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private ConnectionFactory connectionFactory;
    private String username;
    private String password;

    private Connection activeConnection;

    // Constructors ----------------------------------------------------------------------------------------------------

    /**
     * @param username may be null, and in this case we create an anonymous connection.
     */
    public ConnectionManager(ConnectionFactory connectionFactory, String username, String password) {

        this.connectionFactory = connectionFactory;
        this.username = username;
        this.password = password;
    }

    // Public ----------------------------------------------------------------------------------------------------------

    /**
     * Gets the currently active connection. The current active connection is in a starte state. The active connection
     * may be recreated and subsequently started any time by invoking recreateConnection();
     *
     * @see ConnectionManager#recreateConnection()
     */
    public synchronized Connection getConnection() {

        if (activeConnection == null) {

            try {
                recreateConnection();
            }
            catch(JMSException e) {

                throw new RuntimeException("failed to create a connectino the first time we tried", e);
            }
        }

        return activeConnection;
    }

    /**
     * @throws JMSException on recreating connection
     */
    public synchronized Connection recreateConnection() throws JMSException {

        if (username != null) {

            activeConnection = connectionFactory.createConnection(username, password);
        }
        else {

            activeConnection = connectionFactory.createConnection();
        }

        activeConnection.start();
        log.info(activeConnection + " started");
        return activeConnection;
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
