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

import java.io.ByteArrayOutputStream;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/5/17
 */
public class MockSocket extends Socket {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private BlockableInputStream bis;
    private ByteArrayOutputStream baos;
    private boolean closed;

    // Constructors ----------------------------------------------------------------------------------------------------

    public MockSocket() {

        this(null);
    }

    public MockSocket(String inputStreamContent) {

        this.bis = inputStreamContent == null ?
                new BlockableInputStream(new byte[0]) :
                new BlockableInputStream(inputStreamContent.getBytes());

        this.baos = new ByteArrayOutputStream();
    }

    // Overrides -------------------------------------------------------------------------------------------------------

    @Override
    public BlockableInputStream getInputStream() {

        return bis;
    }

    @Override
    public ByteArrayOutputStream getOutputStream() {

        return baos;
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

        this.baos = new ByteArrayOutputStream();
    }

    /**
     * Configure this instance to block on readReleaseLatch after reading n characters and while "reading" the n + 1
     * character. Right before blocking, the method releases readBlocked latch, if not null, to let the caller know that
     * it is about to block.
     *
     * @param readWithoutBlocking the number of character to read without blocking. read() will block while "reading"
     *                            the n + 1 character.
     *
     * @param readBlocked may be null, in which case the latch won't be released.
     * @param readReleaseLatch can be used to release read(), cannot be null
     */
    public void blockInReadingAfterTheSpecifiedNumberOfCharacters(
            int readWithoutBlocking, CountDownLatch readBlocked, CountDownLatch readReleaseLatch) {

        bis.blockInReadingAfterTheSpecifiedNumberOfCharacters(readWithoutBlocking, readBlocked, readReleaseLatch);
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
