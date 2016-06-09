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

import io.novaordis.playground.jee.servlet.session.plumbing.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 6/9/16
 */
public class CommandFactory {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(CommandFactory.class);

    // Static ----------------------------------------------------------------------------------------------------------

    public static Command build(HttpServletRequest request) throws Exception {

        String path = request.getPathInfo();

        log.debug("path: " + path);

        if ("/".equals(path)) {

            return new Info();
        }

        throw new HttpException(404, "no known command can be inferred from " + path);


//        log.info("GET " + path);
//
//        //
//        // session handling
//        //
//
//        // look up session, but DO NOT create it if it's not there
//        HttpSession session = req.getSession(false);
//        log.info("HTTP session " + session);
//
//        // figure out whether we shall establish a session
//        if (shallWeEstablishSession(req)) {
//
//            if (session != null) {
//
//                // session already established - we don't support this case yet
//                throw new ServletException("HTTP session already established,  we don't currently re-process 'establish-session=true'");
//            }
//
//            // no session, create one
//            session = req.getSession(true);
//            log.info("created HTTP session " + session);
//        }
//
//        dumpHeaders(req);
//
//        String operationLog = interactWithCache(session, req);
//
//
//        sendResponse(, , , user, remoteAddress, remoteHost, cookies, operationLog);

    }

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    private CommandFactory() {
    }

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
