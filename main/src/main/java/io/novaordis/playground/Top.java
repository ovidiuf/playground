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

package io.novaordis.playground;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 4/28/17
 */
public class Top {

    // Constants -------------------------------------------------------------------------------------------------------

    // Wed May 24 18:59:08 EDT 2017
    private static final DateFormat INPUT_DATE_FORMAT = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
    private static final DateFormat OUTPUT_DATE_FORMAT = new SimpleDateFormat("MM/dd/yy HH:mm:ss");

    // Static ----------------------------------------------------------------------------------------------------------

    public static void queueDepths(String[] args) throws Exception {

        String filename = args[0];

        File f = new File(filename);

        BufferedReader br = new BufferedReader(new FileReader(f));

        String line;

        Date current = null;
        int count = 0;

        List<Reading> readings = new ArrayList<>();

        Reading crt = null;

        int lineNumber = 0;

        while((line = br.readLine()) != null) {

            lineNumber ++;

            line = line.trim();

            if (line.isEmpty()) {
                continue;
            }

            try {

                Date d = INPUT_DATE_FORMAT.parse(line);

                if (d != null) {

                    if (crt != null) {

                        readings.add(crt);
                    }

                    crt = new Reading(d);
                    continue;
                }
            }
            catch(Exception e) {

                //
                // ignore
                //
            }

            //
            // drop "ignorable" lines
            //

            if (line.startsWith("==")) {

                continue;
            }

            //
            // queue depths
            //

            int i = line.indexOf(' ');

            if (i == -1) {

                throw new IllegalArgumentException("invalid line " + lineNumber + ", missing space");
            }

            String cs = line.substring(0, i);
            String queueName = line.substring(i + 1);
            int depth = Integer.parseInt(cs);

            crt.addDepth(queueName, depth);
        }

        br.close();

        processReadings(readings);

    }

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    private static void processReadings(List<Reading> readings) {

        Set<String> queueNames = new HashSet<>();

        for(Reading r: readings) {

            queueNames.addAll(r.depths.keySet());
        }

        List<String> queueNameList = new ArrayList<>(queueNames);
        Collections.sort(queueNameList);

        String headers = "timestamp, ";
        for(String name: queueNameList) {

            headers += name + ", ";
        }

        System.out.println(headers);

        for(Reading r: readings) {

            String csvLine = "";

            Date d = r.date;

            csvLine += OUTPUT_DATE_FORMAT.format(d) + ", ";

            for(String name: queueNameList) {

                QueueDepth qd = r.depths.get(name);

                if (qd == null) {

                    csvLine += "0";
                }
                else {

                    csvLine += qd.depth;
                }

                csvLine += ", ";
            }

            System.out.println(csvLine);
        }

    }

    // Inner classes ---------------------------------------------------------------------------------------------------

    private static class Reading {

        public Date date;

        private Map<String, QueueDepth> depths = new HashMap<>();

        public Reading(Date d) {

            this.date = d;
        }

        public void addDepth(String queueName, int depth) {

            depths.put(queueName, new QueueDepth(queueName, depth));
        }
    }

    private static class QueueDepth {

        public String name;
        public int depth;

        public QueueDepth(String name, int depth) {

            this.name = name;
            this.depth = depth;
        }
    }
}
