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

import io.novaordis.playground.http.server.http.HttpResponse;
import io.novaordis.playground.http.server.http.InvalidHttpMessageException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * A mock connection that simulates various situations occurred in reading and writing.
 *
 * It can simulate blocking in reading after n-th character.
 *
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/5/17
 */
public class MockConnection extends Connection {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private List<byte[]> flushedOutputContent;

    // Constructors ----------------------------------------------------------------------------------------------------

    public MockConnection() throws IOException {

        this(null);
    }

    public MockConnection(long id) throws IOException {

        super(id, new MockSocket(), true, null);
        this.flushedOutputContent = new ArrayList<>();
    }

    public MockConnection(String inputStreamContent) throws IOException {

        this(0, inputStreamContent);
    }

    public MockConnection(long id, String inputStreamContent) throws IOException {

        super(id, new MockSocket(inputStreamContent), true, null);
        this.flushedOutputContent = new ArrayList<>();
    }

    // Overrides -------------------------------------------------------------------------------------------------------

    @Override
    public void flush() {

        //
        // save flushed content
        //

        recordOutputChunkAccumulatedSoFar();
    }

    @Override
    public void close() {

        //
        // save flushed content
        //

        recordOutputChunkAccumulatedSoFar();

        super.close();
    }

    // Public ----------------------------------------------------------------------------------------------------------

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

        ((MockSocket)getSocket()).blockInReadingAfterTheSpecifiedNumberOfCharacters(
                readWithoutBlocking, readBlocked, readReleaseLatch);
    }

    /**
     * @return the chunks of content followed by flushes. close() count as a flush. However, if close() follows
     * immediately after a flush(), no byte[0] will be added to the list.
     */
    public List<byte[]> getFlushedOutputContent() {

        return flushedOutputContent;
    }

    /**
     * @return the number of bytes left to be read until EOS is reached. If we are at the EOS, the method returns 0.
     */
    public long bytesLeftToEOS() {

        BlockableInputStream bis = ((MockSocket)getSocket()).getInputStream();
        return bis.bytesLeftToEOS();
    }

    public HttpResponse getLastResponse() throws InvalidHttpMessageException {

        List<byte[]> c = getFlushedOutputContent();

        if (c.size() > 1) {
            throw new RuntimeException("NOT YET IMPLEMENTED: don't know how to handle more than one flushed chunk");
        }

        return new HttpResponse(c.get(0));
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    private void recordOutputChunkAccumulatedSoFar() {

        MockSocket ms = (MockSocket)getSocket();

        ByteArrayOutputStream baos = ms.getOutputStream();
        byte[] bytes = baos.toByteArray();

        if (bytes.length > 0) {

            flushedOutputContent.add(bytes);
            ms.resetOutputStream();
        }
    }

    // Inner classes ---------------------------------------------------------------------------------------------------

}
