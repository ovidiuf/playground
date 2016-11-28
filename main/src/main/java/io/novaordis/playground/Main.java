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

package io.novaordis.playground;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 10/5/16
 */
public class Main {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    public static void main(String[] args) throws Exception {

        hist(args);
    }

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    private static final DateFormat INPUT_DATE_FORMAT = new SimpleDateFormat("dd/MMM/yy:HH:mm:ss");
    private static final DateFormat OUTPUT_DATE_FORMAT = new SimpleDateFormat("MM/dd/yy HH:mm:ss");

    private static void hist(String[] args) throws Exception {

        String filename = args[0];

        File f = new File(filename);


        BufferedReader br = new BufferedReader(new FileReader(f));

        String line;

        Date current = null;
        int count = 0;

        while((line = br.readLine()) != null) {

            Date d = INPUT_DATE_FORMAT.parse(line);

            if (current == null) {

                current = d;
                count ++;
            }
            else if (current.equals(d)) {
                count ++;
            }
            else if (current.compareTo(d) > 0) {

                throw new Exception(current + " is after " + d);
            }
            else {

                System.out.println(OUTPUT_DATE_FORMAT.format(current) + ", " + count);
                current = d;
                count = 1;
            }
        }

        br.close();
    }

    // Inner classes ---------------------------------------------------------------------------------------------------

}
