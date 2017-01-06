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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.Socket;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/5/17
 */
public class MockSocket extends Socket {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private InputStream is;
    private ByteArrayOutputStream os;
    private boolean closed;

    // Constructors ----------------------------------------------------------------------------------------------------

    public MockSocket() {

        this(null);
    }

    public MockSocket(String requestContent) {

        this.is = requestContent == null ?
                new ByteArrayInputStream(new byte[0]) :
                new ByteArrayInputStream(requestContent.getBytes());

        this.os = new ByteArrayOutputStream();
    }

    // Overrides -------------------------------------------------------------------------------------------------------

    @Override
    public InputStream getInputStream() {

        return is;
    }

    @Override
    public ByteArrayOutputStream getOutputStream() {

        return os;
    }

    @Override
    public void close() {

        this.closed = true;
    }

    @Override
    public boolean isClosed() {

        return closed;
    }

    // Public ----------------------------------------------------------------------------------------------------------

    public void resetOutputStream() {

        this.os = new ByteArrayOutputStream();
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
