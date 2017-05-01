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

package io.novaordis.playground.jee.ejb.ejb2rest.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 5/1/17
 */
public class Measurements {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(Measurements.class);

    // Static ----------------------------------------------------------------------------------------------------------

    public static void invokeSeriallyInALoop(Callee callee, int invocationCount) {

        long[] callDurationNanoseconds = new long[invocationCount];

        log.info("invoking serially in a loop, " + invocationCount + " invocations ...");

        for(int i = 0; i < invocationCount; i ++) {

            long t0 = System.nanoTime();

            callee.businessMethodA("test " + i);

            long t1 = System.nanoTime();

            callDurationNanoseconds[i] = t1 - t0;
        }

//        String s = "";
        double total = 0d;

        for (int i =  0; i < invocationCount; i ++) {

            total += callDurationNanoseconds[i];
//            s += callDurationNanoseconds[i];

//            if (i < invocationCount - 1) {
//
//                s += ", ";
//            }
        }

//        log.info(s);

        double averageNs = total / invocationCount;
        int averageMicroSecs = (int)(averageNs / 1000);

        log.info("average call duration " + averageMicroSecs + " microseconds");
    }


    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
