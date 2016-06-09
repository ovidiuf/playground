package io.novaordis.playground.jee.servlet.session;

import io.novaordis.playground.jee.servlet.session.plumbing.HttpException;
import io.novaordis.playground.jee.servlet.session.plumbing.Result;
import io.novaordis.playground.jee.servlet.session.plumbing.Context;
import io.novaordis.playground.jee.servlet.session.plumbing.command.Command;
import io.novaordis.playground.jee.servlet.session.plumbing.command.CommandFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author <a href="mailto:ovidiu@novaordis.com">Ovidiu Feodorov</a>
 *
 * Copyright 2016 Nova Ordis LLC
 */
public class SessionServlet extends HttpServlet
{
    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(SessionServlet.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // HttpServlet overrides -------------------------------------------------------------------------------------------

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Context context = null;

        try {

            context = new Context(request, response);

            Command command = CommandFactory.build(request);

            command.execute();

            log.info(command + " command executed");

            new Result(context, command).render();
        }
        catch(Exception e) {

            int statusCode = e instanceof HttpException ? ((HttpException)e).getStatusCode(): 500;

            response.setStatus(statusCode);

            new Result(context, e).render();
        }
    }

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------
}
