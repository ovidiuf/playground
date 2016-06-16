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

import io.novaordis.playground.jee.servlet.session.Constants;
import io.novaordis.playground.jee.servlet.session.applicaton.ApplicationType;
import io.novaordis.playground.jee.servlet.session.plumbing.Console;
import io.novaordis.playground.jee.servlet.session.plumbing.Context;
import io.novaordis.playground.jee.servlet.session.plumbing.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.StringTokenizer;

/**
 * Displays the names of the attributes maintained by the session.
 *
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 6/9/16
 */
public class Attributes extends CommandBase {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(Attributes.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    public Attributes(Context context) throws HttpException {

        super(context);
    }

    // Command implementation ------------------------------------------------------------------------------------------

    @Override
    public void execute() throws Exception {

        HttpSession session = getContext().getSession();
        Console console = getContext().getConsole();

        if (session == null) {

            console.warn("no active session, there are no attributes. Try establishing a session with /establish-session");
            return;
        }

        Enumeration<String> ne = session.getAttributeNames();

        if (!ne.hasMoreElements()) {
            console.warn("no attributes in session");
        }
        else {

            String names = "";

            while (ne.hasMoreElements()) {

                String n = ne.nextElement();
                names += n + "\n";
            }

            console.info(names);
        }
    }

    // Public ----------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {

        return "attributes";
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
