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

package io.novaordis.playground.wildfly.hornetq.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Wraps around a blocking queue on which it gets information on messages that were *sent* (or failure details).
 * In order to shut it down, send a NO_MORE_MESSAGES on the queue or call close() directly.
 * Writes statistics externally. Initially developed with the goal of comparing sent messages with received messages in
 * failover scenarios.
 *
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 5/16/16
 */
public class MessageRecordingFacility implements Runnable {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(MessageRecordingFacility.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private boolean closed;

    private BlockingQueue<MessageInfo> queue;
    private Thread thread;

    private BufferedWriter bw;

    private long counter;

    // Constructors ----------------------------------------------------------------------------------------------------

    /**
     * It automatically starts the processing thread upon construction.
     */
    public MessageRecordingFacility(String fileName) throws Exception {

        if (fileName == null) {
            throw new IllegalArgumentException("null file name");
        }

        this.queue = new ArrayBlockingQueue<>(1000);

        this.bw = new BufferedWriter(new FileWriter(fileName));

        writeHeader();

        this.thread = new Thread(this, "Message Recording Thread");
        this.thread.start();
    }

    // Runnable implementation -----------------------------------------------------------------------------------------

    public void run() {

        while(!closed) {

            try {

                MessageInfo mi = queue.take();

                if (MessageInfo.NO_MORE_MESSAGES.equals(mi)) {
                    close();
                }
                else {
                    writeLine(mi);
                }
            } catch (Exception e) {
                throw new RuntimeException("failed to process MessageInfo", e);
            }
        }
    }

    // Public ----------------------------------------------------------------------------------------------------------

    public BlockingQueue<MessageInfo> getQueue() {
        return queue;
    }

    /**
     * Closes the output stream and winds the internal thread down. Once closed, the instance cannot be used anymore.
     */
    public void close() {

        if (closed) {
            return;
        }

        closed = true;

        // shut down the thread (possibly redundantly)
        queue.add(MessageInfo.NO_MORE_MESSAGES);

        try {
            bw.close();
        }
        catch(Exception e) {

            log.error("failed to close the output stream", e);
        }
    }

    // Package protected -----------------------------------------------------------------------------------------------


    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    private void writeHeader() throws Exception {
        bw.write("counter, message-id,\n");
    }

    private void writeLine(MessageInfo mi) throws Exception {

        String line = counter ++ + ", ";

        Exception e = mi.getException();

        if (e != null) {
            throw new RuntimeException("NOT YET IMPLEMENTED dkd32g");
        }

        String messageId = mi.getMessageId();

        line += messageId + ",";
        line += "\n";

        bw.write(line);
    }

    // Inner classes ---------------------------------------------------------------------------------------------------

}
