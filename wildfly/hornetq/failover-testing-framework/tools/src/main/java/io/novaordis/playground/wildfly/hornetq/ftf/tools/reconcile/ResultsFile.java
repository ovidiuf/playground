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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 5/16/16
 */
public class ResultsFile {

    // Constants -------------------------------------------------------------------------------------------------------

    public static final Logger log = LoggerFactory.getLogger(ResultsFile.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private List<Message> messages;
    private List<Failure> failures;

    // Constructors ----------------------------------------------------------------------------------------------------

    public ResultsFile(File f) throws Exception {

        messages = new ArrayList<>();
        failures = new ArrayList<>();

        BufferedReader br = null;

        try {

            br = new BufferedReader(new FileReader(f));

            String line;

            Header header = null;

            while((line = br.readLine()) != null) {

                if (line.startsWith("counter")) {

                    if (header != null) {

                        Header newHeader = new Header(line);

                        if (header.equals(newHeader)) {
                            //
                            // that is fine, it's the result of appending to the bottom of the file
                            //
                            log.warn("duplicate header found");
                            continue;
                        }

                        throw new RuntimeException(
                                "different header found, old header: " + header + ", new header " + newHeader);
                    }

                    header = new Header(line);
                }
                else {

                    ResultLine rl = new ResultLine(header, line);
                    Event e = rl.toEvent();

                    if (e instanceof Message) {
                        messages.add((Message)e);
                    }
                    else if (e instanceof Failure) {
                        failures.add((Failure)e);
                    }
                    else {
                        throw new IllegalStateException("unknown event type: " + e);
                    }
                }
            }
        }
        finally {
            if (br != null) {
                br.close();
            }
        }
    }

    // Public ----------------------------------------------------------------------------------------------------------

    public List<Event> getEvents() {

        List<Event> events = new ArrayList<>(messages);
        events.addAll(failures);
        return events;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public List<Failure> getFailures() {
        return failures;
    }


    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
