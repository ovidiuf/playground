package io.novaordis.playground.openshift.applications.restservice;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author <a href="mailto:ovidiu@novaordis.com">Ovidiu Feodorov</a>
 *
 * Copyright 2016 Nova Ordis LLC
 */
public class RestServlet extends HttpServlet {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = new StdoutLogger(RestServlet.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // HttpServlet overrides -------------------------------------------------------------------------------------------

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Context context = new Context(request, response);

        try {

            context.init();

            Command command = CommandFactory.build(context);

            command.execute();

            log.info(command + " command executed");

            new Result(context, command).render();
        }
        catch(Exception e) {

            int statusCode;

            if (e instanceof HttpException) {

                statusCode = ((HttpException)e).getStatusCode();

            }
            else {

                statusCode = 500;
                log.error("unexpected exception", e);
            }

            response.setStatus(statusCode);

            try {

                new Result(context, e).render();
            }
            catch(Exception e2) {

                //
                // this is when result rendering malfunctions on error, do it manually
                //

                log.error("error rendering failed because of exception", e2);
                response.setStatus(500);
            }
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            log.info("POST: " + request);
    }

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------
}
