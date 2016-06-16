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

import io.novaordis.playground.jee.servlet.session.plumbing.Context;
import io.novaordis.playground.jee.servlet.session.plumbing.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 6/9/16
 */
public class CommandFactory {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(CommandFactory.class);

    // Static ----------------------------------------------------------------------------------------------------------

    public static Command build(Context context) throws Exception {

        String path = context.getRequest().getPathInfo();

        log.debug("path: " + path);

        if ("/".equals(path)) {

            return new Info(context);
        }
        else if (path.startsWith("/test")) {
            return new Test(context);
        }
        else if (path.startsWith("/establish-session")) {
            return new EstablishSession(context, path.substring("/establish-session".length()));
        }
        else if (path.startsWith("/describe-session")) {
            return new DescribeSession(context, path.substring("/describe-session".length()));
        }
        else if (path.startsWith("/invalidate-session")) {
            return new InvalidateSession(context, path.substring("/invalidate-session".length()));
        }
        else if (path.startsWith("/write")) {
            return new Write(context, path.substring("/write".length()));
        }
        else if (path.startsWith("/read")) {
            return new Read(context, path.substring("/read".length()));
        }
        else if (path.startsWith("/attributes")) {
            return new Attributes(context);
        }

        throw new HttpException(404, "no known command can be inferred from " + path);
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
