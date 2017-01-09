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

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/9/17
 */
public abstract class ManagementConsoleMBeanTest {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    // Tests -----------------------------------------------------------------------------------------------------------

    @Test
    public void getConnectionCount() throws Exception {

        ManagementConsoleMBean mc = getManagementConsoleMBeanToTest();
        assertEquals(0, mc.getConnectionCount());
    }

    @Test
    public void listConnections() throws Exception {

        ManagementConsoleMBean mc = getManagementConsoleMBeanToTest();
        String s = mc.listConnections();
        assertEquals("no live connections", s);
    }

    @Test
    public void listClosedConnections() throws Exception {

        ManagementConsoleMBean mc = getManagementConsoleMBeanToTest();
        String s = mc.listClosedConnections();
        assertEquals("no closed connections", s);
    }

    @Test
    public void clearClosedConnectionHistory()throws Exception {

        ManagementConsoleMBean mc = getManagementConsoleMBeanToTest();
        mc.clearClosedConnectionHistory();
    }

    @Test
    public void releaseDelayedRequest()throws Exception {

        ManagementConsoleMBean mc = getManagementConsoleMBeanToTest();
        mc.releaseDelayedRequest(0);
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    protected abstract ManagementConsoleMBean getManagementConsoleMBeanToTest() throws Exception;

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
