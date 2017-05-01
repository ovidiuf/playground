package io.novaordis.playground.jee.ejb.ejb2rest.caller;

import javax.ejb.Local;

/**
 * The interface used by the wrapper servlet to call into the Caller and initiate the EJB-to-EJB call (Caller to Callee)
 *
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 5/1/17
 */
@Local
public interface LocalAccessToCaller {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    void triggerRemoteCall();

}
