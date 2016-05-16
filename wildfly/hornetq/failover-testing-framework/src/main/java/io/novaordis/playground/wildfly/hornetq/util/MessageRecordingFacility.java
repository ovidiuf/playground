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

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Wraps around a blocking queue on which it gets information on messages that were *sent* (or failure details). Writes
 * statistics externally. Initially developed* with the goal of comparing sent messages with received messages in
 * failover scenarios.
 *
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 5/16/16
 */
public class MessageRecordingFacility implements Runnable {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private BlockingQueue<MessageInfo> queue;
    private Thread thread;

    // Constructors ----------------------------------------------------------------------------------------------------

    /**
     * It automatically starts the processing thread upon construction.
     */
    public MessageRecordingFacility() {

        this.queue = new ArrayBlockingQueue<MessageInfo>(1000);
        this.thread = new Thread(this, "Message Recording Thread");
        this.thread.start();
    }

    // Runnable implementation -----------------------------------------------------------------------------------------

    public void run() {

        try {

            MessageInfo mi = queue.take();

            System.out.println(mi.getMessageId());
        }
        catch(Exception e) {
            throw new RuntimeException("failed to process MessageInfo", e);
        }
    }

    // Public ----------------------------------------------------------------------------------------------------------

    public BlockingQueue<MessageInfo> getQueue() {
        return queue;
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
