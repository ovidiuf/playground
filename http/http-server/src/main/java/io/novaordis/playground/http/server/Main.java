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

import io.novaordis.playground.http.server.jmx.ManagementConsole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

/**
 * The local port to listen on must be specified as the first argument.
 *
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/4/17
 */
public class Main {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    public static void main(String[] args) throws Exception {

        Configuration c = new Configuration(args);

        Server server = new Server(c);

        registerWithMBeanServer(server.getManagementConsole());

        server.listen();

        log.info("http server ready to accept connections ...");
    }

    // Package protected -----------------------------------------------------------------------------------------------

    static void registerWithMBeanServer(ManagementConsole mc) throws Exception {

        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

        ObjectName n = new ObjectName("novaordis:service=http-server");
        mBeanServer.registerMBean(mc, n);

        log.debug("server registered with the JVM MBean server as " + n);
    }

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
