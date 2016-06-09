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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 6/9/16
 */
public class EstablishSession {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    private boolean shallWeEstablishSession(HttpServletRequest r) {

        String s = r.getParameter("establish-session");
        return s != null;
    }

    /**
     * @return a human-readable report ("A=B was stored in the session")
     */
    private String interactWithCache(HttpSession session, HttpServletRequest req) throws ServletException {

        String path = req.getPathInfo();
        String key = req.getParameter("key");
        String value = req.getParameter("value");

        if ("/put".equals(path)) {

            if (session == null) {
                throw new ServletException("no active session, cannot put, use /&establish-session");
            }

            if (key == null) {
                throw new ServletException("null key, use /put?key=...&value=...");
            }

            if (value == null) {
                throw new ServletException("null value, use /put?key=...&value=...");
            }

            session.setAttribute(key, value);

            return "\"" + key + "\"=\"" + value + "\" stored in session " + session.getId();
        }
        else if ("/get".equals(path)) {

            if (session == null) {
                throw new ServletException("no active session, cannot get, use /&establish-session");
            }

            if (key == null) {
                throw new ServletException("null key, use /get?key=...");
            }

            Object v = session.getAttribute(key);

            return "\"" + key + "\"=\"" + v + "\" retrieved from session " + session.getId();
        }

        return null;
    }



    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
