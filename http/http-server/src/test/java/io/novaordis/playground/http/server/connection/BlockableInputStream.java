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

import io.novaordis.playground.http.server.http.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.util.concurrent.CountDownLatch;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/6/17
 */
public class BlockableInputStream extends ByteArrayInputStream {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(BlockableInputStream.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------


    // the number of bytes read from the stream
    private long bytesRead;
    private long readWithoutBlocking;
    private CountDownLatch readBlocked;
    private CountDownLatch readReleaseLatch;

    private volatile boolean hasReachedEOS;

    // Constructors ----------------------------------------------------------------------------------------------------

    public BlockableInputStream(byte[] buffer) {

        super(buffer);

        this.bytesRead = 0L;
        this.readWithoutBlocking = -1L;
    }

    // Overrides -------------------------------------------------------------------------------------------------------

    /**
     * This is where we count and block.
     */
    @Override
    public int read() {

        if (bytesRead == readWithoutBlocking) {

            //
            // block while "reading" the next byte
            //

            if (readBlocked != null) {

                readBlocked.countDown();
            }

            log.info(Thread.currentThread().getName() + " blocking in " + this + ".read()");

            try {

                readReleaseLatch.await();
            }
            catch(InterruptedException e) {

                //
                // illegal state
                //
                throw new IllegalArgumentException("interrupted while blocked on the read release latch", e);
            }

            log.info(Thread.currentThread().getName() + " released from blocking read()");
        }

        int i = super.read();

        if (i == -1) {

            hasReachedEOS = true;
        }
        else {

            bytesRead ++;
            log.info("byte " + (bytesRead - 1) +
                    " (" + HttpUtil.toPrintableSymbol(i)+ ") successfully read from the mock socket");
        }

        return i;
    }

    @Override
    public int read(@SuppressWarnings("NullableProblems") byte[] buffer) {

        //
        // TODO: if we ever need to implement this, make sure it takes into account 'readWithoutBlocking'
        //
        throw new UnsupportedOperationException("read(byte[])");
    }

    @Override
    public int read(byte b[], int off, int len) {

        //
        // TODO: if we ever need to implement this, make sure it takes into account 'readWithoutBlocking'
        //

        throw new UnsupportedOperationException("read(byte[], int, int)");
    }

    @Override
    public synchronized long skip(long n) {

        //
        // TODO: if we ever need to implement this, make sure it takes into account 'readWithoutBlocking'
        //

        throw new UnsupportedOperationException("skip(long)");
    }

    @Override
    public synchronized int available() {

        //
        // TODO: if we ever need to implement this, make sure it takes into account 'readWithoutBlocking'
        //

        throw new UnsupportedOperationException("available()");
    }

    @Override
    public boolean markSupported() {

        //
        // TODO: if we ever need to implement this, make sure it takes into account 'readWithoutBlocking'
        //

        throw new UnsupportedOperationException("markSupported()");
    }

    @Override
    public void mark(int readAheadLimit) {

        //
        // TODO: if we ever need to implement this, make sure it takes into account 'readWithoutBlocking'
        //

        throw new UnsupportedOperationException("mark(int)");
    }

    @Override
    public synchronized void reset() {

        //
        // TODO: if we ever need to implement this, make sure it takes into account 'readWithoutBlocking'
        //

        throw new UnsupportedOperationException("reset()");
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
            long readWithoutBlocking, CountDownLatch readBlocked, CountDownLatch readReleaseLatch) {

        if (readReleaseLatch == null) {
            throw new IllegalArgumentException("null read release latch");
        }

        this.readWithoutBlocking = readWithoutBlocking;
        this.readBlocked = readBlocked;
        this.readReleaseLatch = readReleaseLatch;
    }

    public boolean hasReachedEOS() {

        return hasReachedEOS;
    }

    /**
     * @return the number of bytes left to be read from this stream until EOS is reached. If we are at the EOS, the
     * method returns 0.
     */
    public long bytesLeftToEOS() {

        return count - bytesRead;
    }

    @Override
    public String toString() {

        return "BlockableInputStream[" + Integer.toHexString(System.identityHashCode(this)) + "]";
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
