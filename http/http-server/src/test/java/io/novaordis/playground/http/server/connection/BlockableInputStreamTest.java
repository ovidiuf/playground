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

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/6/17
 */
public class BlockableInputStreamTest  {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(BlockableInputStreamTest.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Tests -----------------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    @Test
    public void noBlocking() throws Exception {

        BlockableInputStream bis = new BlockableInputStream("12345".getBytes());

        assertEquals('1', (char)bis.read());
        assertEquals('2', (char)bis.read());
        assertEquals('3', (char)bis.read());
        assertEquals('4', (char)bis.read());
        assertEquals('5', (char)bis.read());
        assertEquals(-1, bis.read());
        assertEquals(-1, bis.read());
    }

    @Test
    public void blockAfterNCharacters() throws Exception {

        blockAfterNCharacters("12345", 3);
    }

    @Test
    public void blockAfterNCharacters_RightBeforeEnd() throws Exception {

        blockAfterNCharacters("12345", 4);
    }

    @Test
    public void blockAfterNCharacters_RightOnTheEnd() throws Exception {

        blockAfterNCharacters("12345", 5);
    }

    // bytesLeftToEOS() ------------------------------------------------------------------------------------------------

    @Test
    public void bytesLeftToEOS() throws Exception {

        BlockableInputStream bis = new BlockableInputStream("12".getBytes());
        assertEquals(2, bis.bytesLeftToEOS());

        assertEquals('1', bis.read());
        assertEquals(1, bis.bytesLeftToEOS());

        assertEquals('2', bis.read());
        assertEquals(0, bis.bytesLeftToEOS());

        assertEquals(-1, bis.read());
        assertEquals(0, bis.bytesLeftToEOS());

        assertEquals(-1, bis.read());
        assertEquals(0, bis.bytesLeftToEOS());
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    private void blockAfterNCharacters(String content, int readWithoutBlocking) throws Exception {

        BlockableInputStream bis = new BlockableInputStream(content.getBytes());

        // large enough buffer
        final byte[] buffer = new byte[content.length() * 2];

        final CountDownLatch readBlocked = new CountDownLatch(1);
        final CountDownLatch readReleaseLatch = new CountDownLatch(1);
        final Exception[] readingThreadException = new Exception[1];

        bis.blockInReadingAfterTheSpecifiedNumberOfCharacters(readWithoutBlocking, readBlocked, readReleaseLatch);

        //
        // read from a separate thread that will block after the specified number of characters are read
        //
        new Thread(() -> {

            try {

                int index = 0;

                while(true) {

                    int b = bis.read();

                    if (b == -1) {

                        log.info("reached END OF STREAM");
                        return;
                    }

                    log.info("read " + (char)b);
                    buffer[index ++] = (byte)b;
                }
            }
            catch (Exception e) {

                readingThreadException[0] = e;
            }

        }, "reader thread").start();

        //
        // wait until the reader thread blocks
        //

        readBlocked.await();

        log.info("reader thread has blocked in read()");

        //
        // reader thread blocked, I expect to see readWithoutBlocking in buffer
        //
        for(int i = 0; i < readWithoutBlocking; i ++) {

            assertEquals(content.charAt(i), buffer[i]);
        }

        //
        // the rest should be uninitialized
        //

        for(int i = readWithoutBlocking + 1; i < buffer.length; i ++) {

            assertEquals(0, buffer[i]);
        }


        //
        // release the reading thread
        //

        log.info("releasing the reader thread ...");

        readReleaseLatch.countDown();

        //
        // after a while, all bytes should be read
        //

        long t0 = System.currentTimeMillis();
        long timeout = 1000L;

        while (!bis.hasReachedEOS()) {

            if (System.currentTimeMillis() - t0 > timeout) {

                fail("stream has not reached EOS after " + timeout + " ms");
            }

            Thread.sleep(100L);
        }

        //
        // the stream should reach its end
        //

        int i = bis.read();
        assertEquals(-1, i);

        //
        // all content should be in the buffer
        //

        for(i = 0; i < content.length(); i ++) {

            assertEquals(content.charAt(i), buffer[i]);
        }

        if (readingThreadException[0] != null) {

            log.error("the reader thread has thrown exception", readingThreadException[0]);
            fail("the reader thread has thrown exception");
        }
    }


    // Inner classes ---------------------------------------------------------------------------------------------------

}
