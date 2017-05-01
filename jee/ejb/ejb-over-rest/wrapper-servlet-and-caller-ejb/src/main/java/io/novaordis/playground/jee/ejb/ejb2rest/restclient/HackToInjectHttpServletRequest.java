package io.novaordis.playground.jee.ejb.ejb2rest.restclient;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * CDI 1.0 doesn't provide a way to "@Inject HttpServletRequest", so this class provides a
 * workaround, which allows the value to be retrieved from a static ThreadLocal.
 *
 * If we move to an environment supporting CDI 1.1, then we can remove this class and simply use CDI
 * injection to provide the current request to the RestClientFactoryImpl.
 */
@WebListener
public class HackToInjectHttpServletRequest implements ServletRequestListener {

    // Constants -------------------------------------------------------------------------------------------------------

    /**
     * Stores the active request for this thread, if there is one.
     *
     * Why do we use a static ThreadLocal here? Because the instance of
     * {@link HackToInjectHttpServletRequest} that is managed by the servlet container is not
     * available to the CDI container, but the creation and lifecycle management of
     * {@link HackToInjectHttpServletRequest} occurs on the same thread that will be used to handle
     * the request. So, we let the servlet container call the requestInitialized and
     * requestrDetroyed methods to mark when the CURRENT_REQUEST should be set and cleared, and we
     * let code that needs to obtain the current request (for the current thread) get it via the
     * static getCurrentRequest method.
     */
    private static final ThreadLocal<ServletRequest> CURRENT_REQUEST = new ThreadLocal<>();


    // Static ----------------------------------------------------------------------------------------------------------

    /**
     * Makes current {@link HttpServletRequest} available to components of mp-rest-client.
     */
    static Optional<HttpServletRequest> getCurrentRequest() {

        if (CURRENT_REQUEST.get() instanceof HttpServletRequest) {

            return Optional.of((HttpServletRequest) CURRENT_REQUEST.get());
        }

        return Optional.empty();
    }

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // ServletRequestListener implementation ---------------------------------------------------------------------------

    /**
     * Gets called at the end of every servlet request, in the same thread that will handle the
     * request.
     */
    @Override
    public void requestDestroyed(final ServletRequestEvent event) {

        CURRENT_REQUEST.remove();
    }

    /**
     * Gets called at the beginning of every servlet request, in the same thread that will handle
     * the request.
     */
    @Override
    public void requestInitialized(final ServletRequestEvent event) {

        CURRENT_REQUEST.set(event.getServletRequest());
    }

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
