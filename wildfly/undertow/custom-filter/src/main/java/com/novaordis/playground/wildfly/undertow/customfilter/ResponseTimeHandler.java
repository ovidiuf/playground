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

    /**
     * Instantiated by reflection by the Undertow machinery.
     */
    @SuppressWarnings("unused")
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

        StopWatch stopWatch = new StopWatch();
        stopWatch.setT0(System.currentTimeMillis());
        exchange.addExchangeCompleteListener(stopWatch);
        next.handleRequest(exchange);
    }

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    private class StopWatch implements ExchangeCompletionListener {

        // Attributes ------------------------------------------------------------------------------------------------------

        private long t0;

        // ExchangeCompletionListener implementation -------------------------------------------------------------------

        /**
         * This code gets executed on one of XNIO Worker's worker thread (as opposite to the IO threads), after
         * the exchange is completed.
         */
        @Override
        public void exchangeEvent(HttpServerExchange exchange, NextListener nextListener) {

            try {

                // stop the watch and calculate the difference
                long t1 = System.currentTimeMillis();
                log.info("request took " + (t1 - t0) + " ms");
            }
            finally {

                if (nextListener != null) {
                    nextListener.proceed();
                }
            }
        }

        // Public ------------------------------------------------------------------------------------------------------

        public void setT0(long t0) {
            this.t0 = t0;
        }
    }

    // Inner classes ---------------------------------------------------------------------------------------------------

}
