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

package io.novaordis.playground.jee.servlet.session.plumbing;

import io.novaordis.playground.jee.servlet.session.plumbing.command.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

/**
 * The class knows how to render the result of the command (or an exception) to HTML and send it to the client.
 *
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 6/9/16
 */
public class Result {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(Result.class);

    public static final String[] REQUEST_HEADERS_TO_DISPLAY = {
            "host",
            "cache-control",
            "user-agent"
    };

    // Static ----------------------------------------------------------------------------------------------------------

    private static String cookiesToHtml(List<Cookie> cookies) {

        if (cookies.isEmpty()) {
            return "no cookies";
        }
        else {

            String s = "<table>\n";

            int i = 1;
            for(Cookie c: cookies) {

                s += "<tr><td>" + (i++) + ".</td><td>" + cookieToString(c) + "</td></tr>\n";
            }

            s += "</table>\n";

            return s;
        }
    }


    private static String cookieToString(Cookie c)
    {
        StringBuilder sb = new StringBuilder();

        sb.append(c.getName()).append("=").append(c.getValue());

        String path = c.getPath();

        sb.append(path == null ? " (no path)" : " " + path);

        return sb.toString();
    }

    /**
     * @param requestHeadersToDisplay if null or empty list, display all headers in the request header list
     */
    private static String headersToHtml(List<Header> requestHeaders, String... requestHeadersToDisplay) {

        String s = "<table>\n";

        if (requestHeadersToDisplay != null && requestHeadersToDisplay.length > 0) {
            upper: for(String name: requestHeadersToDisplay) {
                for(Header h: requestHeaders) {
                    if (name.equals(h.getName())) {
                        s += "<tr><td align='right'>" + name + ":</td><td>" + h.getValue() + "</td></tr>\n";
                        continue upper;
                    }
                }
                s += "<tr><td>" + name + ":</td><td>null</td></tr>\n";
            }
        }
        else {
            for(Header h: requestHeaders) {
                s += "<tr><td align='right'>" + h.getName() + ":</td><td>" + h.getValue() + "</td></tr>\n";
            }
        }

        s += "</table>\n";

        return s;
    }


    // Attributes ------------------------------------------------------------------------------------------------------

    private HttpServletRequest request;
    private HttpServletResponse response;
    private Context context;
    private Command command;
    private Exception exception;

    // Constructors ----------------------------------------------------------------------------------------------------

    public Result(Context context, Command command) {

        this(context, command, null);
    }

    public Result(Context context, Exception exception) {

        this(context, null, exception);
    }

    /**
     * @param context may be null, we should be prepared to handle this situation.
     */
    private Result(Context context, Command command, Exception exception) {

        this.context = context;
        this.request = context.getRequest();
        this.response = context.getResponse();
        this.command = command;
        this.exception = exception;
    }

    // Public ----------------------------------------------------------------------------------------------------------

    public void render() {

        try {

            response.setContentType("text/html");

            PrintWriter out = response.getWriter();

            out.println("<html>");

            if (exception != null) {

                //
                // report error
                //

                out.println("<head><title>Session Servlet Failure</title></head>");
                out.println("<body>");

                String message;

                if (exception instanceof HttpException) {

                    HttpException he = (HttpException)exception;

                    message = "HTTP status code " + he.getStatusCode() + ": " + he.getMessage();
                }
                else {

                    message = "HTTP status code 500: "+ exception.getClass().getName() + ": " + exception.getMessage();
                }

                out.println(message + "\n");

                out.println("</body>");
            }
            else {

                //
                // report successful execution (even if the command has a domain-specific error to report)
                //

                out.println("<head><title>Session Servlet Response</title></head>");
                out.println("<body>");

                renderInfo(out);
                renderConsoleContent(context.getConsole(), out);

                out.println("</body>");
            }

            out.println("</html>");
        }
        catch(Exception e) {

            throw new RuntimeException("NOT YET IMPLEMENTED");
        }
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    private void renderInfo(PrintWriter out) throws Exception {

        String s = "<table>\n";

        s += "<tr><td align='right' valign='top'>request timestamp:</td><td>" + new Date() + "</td></tr>\n";
        s += "<tr><td align='right' valign='top'>path:</td><td>" + request.getPathInfo() + "</td></tr>\n";
        s += "<tr><td align='right' valign='top'>executing host:</td><td>" + context.getExecutingHost() + "</td></tr>\n";
        s += "<tr><td align='right' valign='top'>remote user:</td><td>" + context.getRemoteUser() + "</td></tr>\n";
        s += "<tr><td align='right' valign='top'>remote address:</td><td>" + context.getRemoteAddress() + "</td></tr>\n";
        s += "<tr><td align='right' valign='top'>remote host:</td><td>" + context.getRemoteHost() + "</td></tr>\n";
        s += "<tr><td align='right' valign='top'>request headers:</td><td>" +
                headersToHtml(context.getRequestHeaders(), REQUEST_HEADERS_TO_DISPLAY) + "</td></tr>\n";
        s += "<tr><td align='right' valign='top'>cookies:</td><td>" + cookiesToHtml(context.getCookies()) + "</td></tr>\n";
        s += "</table>\n";

        out.println(s);
    }

    private void renderConsoleContent(Console console, PrintWriter out) throws Exception {

        String s = "<br><hr size='1' width='97%'><br>\n";

        List<String> content = console.getContent();

        String[] backgroundColor = {
                "#FFA07A", // Light salmon
                "#FFD700", // Gold
                "#E0FFFF" // LightCyan
                };

        int i = -1;

        for(String c: content) {

            i++;

            if (c == null || c.length() == 0) {
                continue;
            }

            s += "<table style='width:99%;align=center;background-color:" + backgroundColor[i] + "'>\n";

            for(StringTokenizer st = new StringTokenizer(c, "\n"); st.hasMoreTokens(); ) {
                s += "<tr><td align='left'>" + st.nextToken() + "</td></tr>\n";
            }

            s += "</table>\n";
            s += "<br>\n";
        }

        out.println(s);
    }

    // Inner classes ---------------------------------------------------------------------------------------------------

}
