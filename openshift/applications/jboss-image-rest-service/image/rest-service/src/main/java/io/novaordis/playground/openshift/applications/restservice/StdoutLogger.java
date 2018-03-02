/*
 * Copyright (c) 2018 Nova Ordis LLC
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

package io.novaordis.playground.openshift.applications.restservice;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 2/26/18
 */
public class StdoutLogger implements Logger {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private Class c;

    // Constructors ----------------------------------------------------------------------------------------------------

    public StdoutLogger(Class c) {

        this.c = c;
    }

    // Logger implementation -------------------------------------------------------------------------------------------

    @Override
    public void info(String s) {

        System.out.println("INFO: " + s);
    }

    @Override
    public void debug(String s) {

        System.out.println("DEBUG: " + s);
    }

    @Override
    public void error(String s, Exception e) {

        System.out.println("ERROR: " + s);

        if (e != null) {

            e.printStackTrace(System.out);
        }
    }

    @Override
    public void error(String s) {

        error(s, null);
    }

    @Override
    public void warn(String s) {

        System.out.println("WARN: " + s);
    }

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
