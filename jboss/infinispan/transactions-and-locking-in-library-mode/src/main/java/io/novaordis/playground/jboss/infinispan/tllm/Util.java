package io.novaordis.playground.jboss.infinispan.tllm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.transaction.Status;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;

/**
 * Generic static tools.
 *
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 5/4/17
 */
public class Util {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(Util.class);

    // Static ----------------------------------------------------------------------------------------------------------

    //
    // cache locally for speed, it's not going to change for the life of the application
    //

    private static TransactionManager TRANSACTION_MANAGER;

    /**
     * Yes, we know it's not thread safe.
     */
    public static TransactionManager getTransactionManager() throws ServletException {

        if (TRANSACTION_MANAGER == null) {

            InitialContext ic = null;

            try {

                ic = new InitialContext();

                TRANSACTION_MANAGER = (TransactionManager) ic.lookup("java:/TransactionManager");

            }
            catch (Exception e) {

                throw new ServletException(e);
            }
            finally {

                try {

                    if (ic != null) {

                        ic.close();
                    }
                } catch (Exception e) {

                    log.warn("failed to close initial context", e);
                }
            }
        }

        return TRANSACTION_MANAGER;
    }

    public static boolean inTransaction() throws ServletException {

        TransactionManager tm = getTransactionManager();


        try {

            Transaction t = tm.getTransaction();

            return t != null && t.getStatus() == Status.STATUS_ACTIVE;
        }
        catch(Exception e) {

            throw new ServletException(e);
        }
    }

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
