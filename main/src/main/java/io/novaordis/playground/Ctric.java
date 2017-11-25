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
import java.util.Date;
import java.util.List;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 4/28/17
 */
public class Ctric {

    // Constants -------------------------------------------------------------------------------------------------------

    // Wed May 24 18:59:08 EDT 2017
    private static final DateFormat INPUT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
    private static final DateFormat OUTPUT_DATE_FORMAT = new SimpleDateFormat("MM/dd/yy HH:mm:ss");

    // Static ----------------------------------------------------------------------------------------------------------

    public static void parse(String[] args) throws Exception {

        String filename = args[0];

        File f = new File(filename);

        BufferedReader br = new BufferedReader(new FileReader(f));

        String line;

        Date current = null;
        int count = 0;

        List<Reading> readings = new ArrayList<>();

        Reading crt = null;

        int lineNumber = 0;

        Date last = null;

        while((line = br.readLine()) != null) {

            lineNumber ++;

            line = line.trim();

            if (line.isEmpty()) {

                continue;
            }

            //[2017-10-17 09:42:17,577]

            if (!line.startsWith("[")) {

                continue;
            }

            String ts = line.substring(1);

            int i = ts.indexOf(']');

            if (i == -1) {

                throw new Exception("invalid format");
            }

            ts = ts.substring(0, i);

            Date d = INPUT_DATE_FORMAT.parse(ts);

            if (last == null) {

                last = d;
            }
            else if (last.getTime() > d.getTime()) {

                throw new Exception("date overlap");
            }

            System.out.println(OUTPUT_DATE_FORMAT.format(d));

//            if (crt != null) {
//
//                readings.add(crt);
//            }
//
//            crt = new Reading(d);
//
//            //
//            // drop "ignorable" lines
//            //
//
//            if (!line.startsWith("KiB Swap:")) {
//
//                continue;
//            }
//
//            //
//            // swap data
//            //
//
//            if (crt == null) {
//
//                throw new IllegalStateException("swap line occurred before timestamp");
//            }
//
//            crt.addSwapReading(lineNumber, line);

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

//        Set<String> queueNames = new HashSet<>();
//
        System.out.println("timestamp, Total Swap (MB), Free Swap (MB), Used Swap (MB)");

        for(Reading r: readings) {

            System.out.println(OUTPUT_DATE_FORMAT.format(r.date) + ", " + (r.totalSwapKb / 1024) + ", " + (r.freeSwapKb / 1024) + ", " + (r.usedSwapKb / 1024));

        }
//
//        List<String> queueNameList = new ArrayList<>(queueNames);
//        Collections.sort(queueNameList);
//
//        String headers = "timestamp, ";
//        for(String name: queueNameList) {
//
//            headers += name + ", ";
//        }
//
//        System.out.println(headers);
//
//        for(Reading r: readings) {
//
//            String csvLine = "";
//
//            Date d = r.date;
//
//            csvLine += OUTPUT_DATE_FORMAT.format(d) + ", ";
//
//            for(String name: queueNameList) {
//
//                QueueDepth qd = r.depths.get(name);
//
//                if (qd == null) {
//
//                    csvLine += "0";
//                }
//                else {
//
//                    csvLine += qd.depth;
//                }
//
//                csvLine += ", ";
//            }
//
//            System.out.println(csvLine);
//        }

    }

// Inner classes ---------------------------------------------------------------------------------------------------

    private static class Reading {

        public Date date;

        private int totalSwapKb;
        private int freeSwapKb;
        private int usedSwapKb;

        public Reading(Date d) {

            this.date = d;
        }

        public void addSwapReading(int lineNumber, String swapLine) {

            if (!swapLine.startsWith("KiB Swap: ")) {

                throw new IllegalArgumentException("not a swap line: " + swapLine);
            }

            String line = swapLine;

            line = line.substring("KiB Swap: ".length());

            int i = line.indexOf("total,");

            if (i == -1) {

                throw new IllegalArgumentException("'total,' not found on line " + lineNumber);
            }

            String s = line.substring(0, i).trim();
            totalSwapKb = Integer.parseInt(s);

            line = line.substring(i + "total,".length());

            i = line.indexOf("free,");

            if (i == -1) {

                throw new IllegalArgumentException("'free,' not found on line " + lineNumber);
            }

            s = line.substring(0, i).trim();
            freeSwapKb = Integer.parseInt(s);

            line = line.substring(i + "free,".length());

            i = line.indexOf("used.");

            if (i == -1) {

                throw new IllegalArgumentException("'used.' not found on line " + lineNumber);
            }

            s = line.substring(0, i).trim();
            usedSwapKb = Integer.parseInt(s);
        }
    }
}
