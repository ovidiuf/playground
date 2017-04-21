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

package io.novaordis.playground.java.threads.mts;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 4/21/17
 */
public class Configuration {

    // Constants -------------------------------------------------------------------------------------------------------

    public static final int DEFAULT_THREAD_COUNT = 1;

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private int threadCount;

    // Constructors ----------------------------------------------------------------------------------------------------

    public Configuration(String[] args) throws Exception {

        threadCount = DEFAULT_THREAD_COUNT;


        for(String s: args) {

            if (s.startsWith("--threads=")) {

                s = s.substring("--threads=".length());
                threadCount = Integer.parseInt(s);
            }
        }
    }

    // Public ----------------------------------------------------------------------------------------------------------

    public int getThreadCount() {

        return threadCount;
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
