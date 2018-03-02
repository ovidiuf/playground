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

import java.io.PrintWriter;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 2/26/18
 */
public class Result {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private Context context;
    private Exception exception;

    // Constructors ----------------------------------------------------------------------------------------------------

    public Result(Context c, Command command) {

        this.context = c;
    }

    public Result(Context c, Exception e) {

        this.context = c;
        this.exception = e;
    }

    // Public ----------------------------------------------------------------------------------------------------------

    public void render() throws Exception {

        HttpServletResponse response = context.getResponse();

        response.setContentType("text/plain");

        PrintWriter out = response.getWriter();

        if (exception != null) {

            //
            // report error
            //

            String message;

            if (exception instanceof HttpException) {

                HttpException he = (HttpException)exception;

                message = he.getStatusCode() + ": " + he.getMessage();
            }
            else {

                message = "500: "+ exception.getClass().getName() + ": " + exception.getMessage();
            }

            out.println(message);
        }
        else {

            //
            // report successful execution (even if the command has a domain-specific error to report)
            //
            renderConsoleContent(context.getConsole(), out);
        }
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    private void renderConsoleContent(Console console, PrintWriter out) throws Exception {

        List<String> content = console.getContent();

        StringBuilder s = new StringBuilder();

        for(String c: content) {

            if (c == null || c.length() == 0) {

                continue;
            }

            for(StringTokenizer st = new StringTokenizer(c, "\n"); st.hasMoreTokens(); ) {

                s.append(st.nextToken()).append("\n");
            }
        }

        out.print(s);
    }


    // Inner classes ---------------------------------------------------------------------------------------------------

}
