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

package io.novaordis.playground.java.regex.simplest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 7/29/17
 */
public class Main {

    // Constants -------------------------------------------------------------------------------------------------------

    public static final Pattern PATTERN = Pattern.compile("a");

    // Static ----------------------------------------------------------------------------------------------------------

    public static void main(String[] args) throws Exception {

        if (args.length == 0) {

            System.err.print("a string must be specified as argument");
            System.exit(1);
        }

        String arg = args[0];

//        matchOnce(arg);

//        matchRepeatedly(arg);

        groupExample(arg);

    }

    public static void matchOnce(String argument) {

        Matcher m = PATTERN.matcher(argument);

        if (m.matches()) {

            System.out.println("The entire string DOES MATCH the regular expression");
        }
        else {
            System.out.println("The entire string DOES NOT MATCH the regular expression");
        }
    }

    public static void matchRepeatedly(String argument) {

        Matcher m = PATTERN.matcher(argument);

        int i = 1;

        while(m.find()) {

            int s = m.start();
            int e = m.end();

            System.out.println("matching subsequence " + i + " starts at " + s + " and ends at " + e);

            i ++;
        }
    }

    public static void groupExample(String argument) {

        //
        // We match words that include (or not) a sequence of "a"s, separated by colons. When we encounter a match
        // we display the sequence of "a", as capturing group 1:
        //
        Pattern PATTERN = Pattern.compile("[b-z]+(a*)[b-z]+:");

        Matcher m = PATTERN.matcher(argument);

        int i = 1;

        while(m.find()) {

            System.out.println("match " + (i ++) + ":");
            System.out.println("       match starts at: " + m.start());
            System.out.println("         match ends at: " + m.end());
            System.out.println(" group count for match: " + m.groupCount());
            System.out.println("    group(0) for match: " + m.group(0));
            System.out.println("    group(1) for match: " + m.group(1));
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
