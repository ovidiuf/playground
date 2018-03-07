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

package io.novaordis.playground.openshift.applications.restservice.command;

import io.novaordis.playground.openshift.applications.restservice.Context;
import io.novaordis.playground.openshift.applications.restservice.HttpException;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 2/26/18
 */
public class Getenv extends CommandBase {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    public Getenv(Context context) {

        super(context);
    }

    // Command implementation ------------------------------------------------------------------------------------------

    @Override
    public void execute() throws HttpException {


        String queryString = getContext().getRequest().getQueryString();

        //
        // we expect to find the name of the environment variable as the first argument of the query string
        //

        if (queryString == null || !queryString.startsWith("arg1=")) {

            throw new HttpException(400, "the name of the environment variable should be specified as &arg1=<name>");
        }

        queryString = queryString.substring("arg1=".length());

        int i = queryString.indexOf("&");

        if (i != -1) {

            queryString = queryString.substring(0, i);
        }

        String envVarName = queryString;

        String s = System.getenv(envVarName);

        getConsole().info(s == null ? envVarName + ": no such environment variable defined" : s);

    }

    // Public ----------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {

        return "Ping[" + Integer.toHexString(System.identityHashCode(this)) + "]";
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
