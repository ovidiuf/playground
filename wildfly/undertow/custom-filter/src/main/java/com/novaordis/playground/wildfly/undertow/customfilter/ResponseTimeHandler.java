package com.novaordis.playground.wildfly.undertow.customfilter;

import io.undertow.server.ExchangeCompletionListener;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/19/16
 */
public class ResponseTimeHandler implements HttpHandler {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(ResponseTimeHandler.class);

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private HttpHandler next;

    // Constructors ----------------------------------------------------------------------------------------------------

    public ResponseTimeHandler(HttpHandler next) {
        this.next = next;
    }

    // HttpHandler implementation --------------------------------------------------------------------------------------

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {

        //
        // it is very important NOT to run anything that could block the thread - this is executed by one of the
        // XNIO Worker's IO threads.
        //

        ExchangeCompletionListener stopWatch = new StopWatch();
        exchange.addExchangeCompleteListener(stopWatch);
        next.handleRequest(exchange);
    }

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    private class StopWatch implements ExchangeCompletionListener {

        // ExchangeCompletionListener implementation -------------------------------------------------------------------

        /**
         * This code gets executed on one of XNIO Worker's worker thread (as opposite to the IO threads), after
         * the exchange is completed.
         */
        @Override
        public void exchangeEvent(HttpServerExchange exchange, NextListener nextListener) {

            log.info("exchange completed");
        }
    }

    // Inner classes ---------------------------------------------------------------------------------------------------

}
