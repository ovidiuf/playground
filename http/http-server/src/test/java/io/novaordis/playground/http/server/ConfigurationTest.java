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

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/8/17
 */
public class ConfigurationTest {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    // Tests -----------------------------------------------------------------------------------------------------------

    @Test
    public void defaults() throws Exception {

        Configuration c = new Configuration(new String[] {"1"});

        assertEquals(1, c.getPort());
        assertEquals(new File("."), c.getDocumentRoot());
        assertTrue(c.isPersistentConnections());
    }

    @Test
    public void persistentConnections() throws Exception {

        Configuration c = new Configuration(new String[] { "1", "persistent-connections=false"});

        assertFalse(c.isPersistentConnections());
    }

    @Test
    public void persistentConnections2() throws Exception {

        Configuration c = new Configuration(new String[] { "1", "/tmp", "persistent-connections=false"});

        assertFalse(c.isPersistentConnections());
    }


    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
