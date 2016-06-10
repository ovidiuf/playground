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

package io.novaordis.playground.jee.servlet.session.plumbing.command;

import io.novaordis.playground.jee.servlet.session.plumbing.Console;
import io.novaordis.playground.jee.servlet.session.plumbing.Context;

/**
 * Test command, displays some test text
 *
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 6/9/16
 */
public class Test extends CommandBase {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    public Test(Context context) {

        super(context);
    }

    // Command implementation ------------------------------------------------------------------------------------------

    @Override
    public void execute() throws Exception {

        Console console = getContext().getConsole();

        console.info("this is some info text");
        console.warn("this is some warning text");
        console.error("this is some error text");
    }

    // Public ----------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return "test";
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
