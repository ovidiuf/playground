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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Enumeration;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 6/9/16
 */
public class DescribeSession extends CommandBase {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(DescribeSession.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    /**
     * @param argumentPath the path that follows after the "/establish-session"
     */
    public DescribeSession(Context context, String argumentPath) {

        super(context, argumentPath);
    }

    // Command implementation ------------------------------------------------------------------------------------------

    @Override
    public void execute() throws Exception {

        //
        // if an existing session was identified, it was injected in the context already
        //

        Context context = getContext();
        Console console = context.getConsole();

        HttpSession session = context.getSession();

        if (session == null) {

            console.warn("no active session");
        }
        else {

            SortedMap<String, String> table = new TreeMap<>();
            table.put("JSESSIONID", context.getCookieValue("JSESSIONID"));
            table.put("in-memory session hash code", Integer.toHexString(System.identityHashCode(session)));
            table.put("session ID", session.getId());
            table.put("session creation time", new Date(session.getCreationTime()).toString());
            table.put("session last access time", new Date(session.getLastAccessedTime()).toString());
            table.put("max inactive interval", session.getMaxInactiveInterval() + " seconds");

            int attributeCount = 0;
            for(Enumeration<String> e = session.getAttributeNames(); e.hasMoreElements(); ) {
                e.nextElement();
                attributeCount++;
            }

            table.put("attribute count", "" + attributeCount);

            console.info(table);
        }
    }

    // Public ----------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return "describe-session";
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
