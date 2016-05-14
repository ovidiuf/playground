package io.novaordis.playground.jee.ejb.mdbwi;

import javax.transaction.Status;

/**
 * @author <a href="mailto:ovidiu@novaordis.com">Ovidiu Feodorov</a>
 */
public class TransactionStatus
{
    // Constants ---------------------------------------------------------------------------------------------------------------------------

    // Static ------------------------------------------------------------------------------------------------------------------------------

    public static String statusToString(int status)
    {
        if (status == Status.STATUS_ACTIVE)
        {
            return "ACTIVE (" + status + ")";
        }
        else if (status == Status.STATUS_MARKED_ROLLBACK)
        {
            return "MARKED ROLLBACK (" + status + ")";
        }
        else if (status == Status.STATUS_PREPARED)
        {
            return "PREPARED (" + status + ")";
        }
        else if (status == Status.STATUS_COMMITTED)
        {
            return "COMMITTED (" + status + ")";
        }
        else if (status == Status.STATUS_ROLLEDBACK)
        {
            return "ROLLED BACK (" + status + ")";
        }
        else if (status == Status.STATUS_UNKNOWN)
        {
            return "UNKNOWN (" + status + ")";
        }
        else if (status == Status.STATUS_NO_TRANSACTION)
        {
            return "NO TRANSACTION (" + status + ")";
        }
        else if (status == Status.STATUS_PREPARING)
        {
            return "PREPARING (" + status + ")";
        }
        else if (status == Status.STATUS_COMMITTING)
        {
            return "COMMITTING (" + status + ")";
        }
        else if (status == Status.STATUS_ROLLING_BACK)
        {
            return "ROLLING BACK (" + status + ")";
        }
        else
        {
            return "INVALID STATUS " + status;
        }
    }

    // Attributes --------------------------------------------------------------------------------------------------------------------------

    // Constructors ------------------------------------------------------------------------------------------------------------------------

    private TransactionStatus()
    {
    }

    // Public ------------------------------------------------------------------------------------------------------------------------------

    // Package protected -------------------------------------------------------------------------------------------------------------------

    // Protected ---------------------------------------------------------------------------------------------------------------------------

    // Private -----------------------------------------------------------------------------------------------------------------------------

    // Inner classes -----------------------------------------------------------------------------------------------------------------------
}



