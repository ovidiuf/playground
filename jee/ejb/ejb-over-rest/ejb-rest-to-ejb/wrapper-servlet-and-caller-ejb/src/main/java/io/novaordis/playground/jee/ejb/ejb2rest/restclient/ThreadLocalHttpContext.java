package io.novaordis.playground.jee.ejb.ejb2rest.restclient;

import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

/**
 * HttpContext that delegates to a ThreadLocal HttpContext.
 */
public class ThreadLocalHttpContext implements HttpContext {

    // Constants -------------------------------------------------------------------------------------------------------

    /**
     * The target instance to which this instance will delegate work.
     */
    @SuppressWarnings("Convert2MethodRef")
    private final ThreadLocal<HttpContext> target =
            ThreadLocal.withInitial(() -> new BasicHttpContext());

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // HttpContext implementation --------------------------------------------------------------------------------------

    @Override
    public Object getAttribute(final String id)
    {
        return target.get().getAttribute(id);
    }

    @Override
    public Object removeAttribute(final String id)
    {
        return target.get().removeAttribute(id);
    }

    @Override
    public void setAttribute(final String id, final Object obj)
    {
        target.get().setAttribute(id, obj);
    }

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    /**
     * Set the target instance.
     */
    void setTarget(final HttpContext target)
    {
        this.target.set(target);
    }

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
