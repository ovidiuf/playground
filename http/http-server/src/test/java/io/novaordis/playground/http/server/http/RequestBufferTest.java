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

package io.novaordis.playground.http.server.http;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/6/17
 */
public class RequestBufferTest {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    // Tests -----------------------------------------------------------------------------------------------------------

    // allDiscardableContent() -----------------------------------------------------------------------------------------

    @Test
    public void allDiscardableContent_EmptyBuffer() throws Exception {

        RequestBuffer buffer = new RequestBuffer();

        assertEquals(0 , buffer.size());

        assertTrue(buffer.allDiscardableContent());
    }

    @Test
    public void allDiscardableContent_DiscardableCharacters() throws Exception {

        RequestBuffer buffer = new RequestBuffer();

        buffer.write(' ');
        buffer.write('\t');
        buffer.write('\r');
        buffer.write('\t');

        assertTrue(buffer.allDiscardableContent());
    }

    @Test
    public void allDiscardableContent_LastOneIsNotDiscardable() throws Exception {

        RequestBuffer buffer = new RequestBuffer();

        buffer.write(' ');
        buffer.write('\t');
        buffer.write('\r');
        buffer.write('\t');
        buffer.write('H');

        assertFalse(buffer.allDiscardableContent());
    }

    // isDiscardableChar() ---------------------------------------------------------------------------------------------

    @Test
    public void spaceIsDiscardable() throws Exception {

        assertTrue(RequestBuffer.isDiscardableChar(' '));
    }

    @Test
    public void crIsDiscardable() throws Exception {

        assertTrue(RequestBuffer.isDiscardableChar('\r'));
    }

    @Test
    public void lfIsDiscardable() throws Exception {

        assertTrue(RequestBuffer.isDiscardableChar('\n'));
    }

    @Test
    public void tabIsDiscardable() throws Exception {

        assertTrue(RequestBuffer.isDiscardableChar('\t'));
    }

    @Test
    public void regularCharIsNotDiscardable() throws Exception {

        assertFalse(RequestBuffer.isDiscardableChar('a'));
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
