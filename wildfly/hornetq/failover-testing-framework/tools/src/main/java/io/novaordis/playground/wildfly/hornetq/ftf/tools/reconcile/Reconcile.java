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

package io.novaordis.playground.wildfly.hornetq.ftf.tools.reconcile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 5/16/16
 */
public class Reconcile {

    // Constants -------------------------------------------------------------------------------------------------------

    public static final Logger log = LoggerFactory.getLogger(Reconcile.class);

    // Static ----------------------------------------------------------------------------------------------------------

    public static void main(String[] args) throws Exception {

        if (args.length < 2) {
            throw new Exception("the sender's file and at least one receiver's file must be provided");
        }

        File sf = new File(args[0]);

        if (!sf.isFile() || !sf.canRead()) {
            throw new Exception(sf + " is not a file or cannot be read");
        }

        ResultsFile sendersFile = new ResultsFile(sf);

        List<ResultsFile> receiverFiles = new ArrayList<>();

        for(int i = 1; i < args.length; i ++) {

            File f = new File(args[i]);
            if (!f.isFile() || !f.canRead()) {
                throw new Exception(f + " is not a file or cannot be read");
            }

            receiverFiles.add(new ResultsFile(f));
        }


        List<Message> receivedMessages = new ArrayList<>();

        for(ResultsFile rf: receiverFiles) {

            // check for duplicates
            receivedMessages.addAll(rf.getMessages());
        }

        List<Message> sentMessages = sendersFile.getMessages();

        log.info("sent messages:         " + sentMessages.size());
        log.info("received messages:     " + receivedMessages.size());


        int sentButNotReceivedCount = sentMessages.size() - receivedMessages.size();

        if (sentButNotReceivedCount == 0) {

            log.info("all sent messages were received");
        }
        else {

            log.info("sent but not received: " + (sentMessages.size() - receivedMessages.size()));
        }

        List<Message> sentButNotReceived = new ArrayList<>();

        for(Message m: sentMessages) {

            if (receivedMessages.remove(m)) {
                // found and removed
                continue;
            }
            else {
                sentButNotReceived.add(m);
            }
        }

        if (!sentButNotReceived.isEmpty()) {

            log.info("messages that were not received: ");

            for (Message m : sentButNotReceived) {
                log.info(m.toString());
            }
        }

        if (!receivedMessages.isEmpty()) {
            throw new Exception("received messages that were never sent: " + receivedMessages.size());
        }
    }

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
