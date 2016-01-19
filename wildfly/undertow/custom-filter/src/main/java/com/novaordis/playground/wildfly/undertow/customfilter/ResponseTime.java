package com.novaordis.playground.wildfly.undertow.customfilter;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/19/16
 */
public class ResponseTime implements HttpHandler {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(ResponseTime.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private HttpHandler next;

    // Constructors ----------------------------------------------------------------------------------------------------

    public ResponseTime(HttpHandler next) {
        this.next = next;
    }

    // HttpHandler implementation --------------------------------------------------------------------------------------

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {

        log.info("before");
        next.handleRequest(exchange);
        log.info("after");
    }

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
