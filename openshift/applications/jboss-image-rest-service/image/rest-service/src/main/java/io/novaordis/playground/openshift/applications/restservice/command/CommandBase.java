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

import io.novaordis.playground.openshift.applications.restservice.Command;
import io.novaordis.playground.openshift.applications.restservice.Console;
import io.novaordis.playground.openshift.applications.restservice.Context;
import io.novaordis.playground.openshift.applications.restservice.Service;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 2/26/18
 */
public abstract class CommandBase implements Command {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private Context context;

    // Constructors ----------------------------------------------------------------------------------------------------

    protected CommandBase(Context context) {

        if (context == null) {

            throw new IllegalArgumentException("null context");
        }

        this.context = context;
    }

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    protected Context getContext() {

        return context;
    }

    protected Console getConsole() {

        return context.getConsole();
    }

    protected Service getService() {

        return context.getService();
    }



    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
