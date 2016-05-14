package io.novaordis.playground.jee.ejb.mdbwi;

import org.apache.log4j.Logger;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.naming.InitialContext;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;

/**
 * @author <a href="mailto:ovidiu@novaordis.com">Ovidiu Feodorov</a>
 *
 * Copyright 2013 Nova Ordis LLC
 */
public class CustomEjbInterceptor
{
    // Constants ---------------------------------------------------------------------------------------------------------------------------

    private static final Logger log = Logger.getLogger(CustomEjbInterceptor.class);

    // Static ------------------------------------------------------------------------------------------------------------------------------

    // Attributes --------------------------------------------------------------------------------------------------------------------------

    // Constructors ------------------------------------------------------------------------------------------------------------------------

    // Public ------------------------------------------------------------------------------------------------------------------------------

    @AroundInvoke
    public Object aroundInvoke(InvocationContext ctx) throws Exception
    {
        log.info(this + ".aroundInvoke(" + ctx + ")");

//        try
//        {
//            throw new Exception();
//        }
//        catch(Exception e)
//        {
//            log.info("TRACER", e);
//        }

        InitialContext ic = null;
        TransactionManager tm = null;

        try
        {
            ic = new InitialContext();
            tm = (TransactionManager)ic.lookup("java:/TransactionManager");
        }
        catch(Exception e)
        {
            log.error("failed to interact with the naming system", e);
        }
        finally
        {
            if (ic != null)
            {
                ic.close();
            }
        }

        if (tm != null)
        {
            Transaction t = tm.getTransaction();

            log.info("transaction: " + t);

            if (t != null)
            {
                log.info("transaction status: " + TransactionStatus.statusToString(t.getStatus()));
                log.info("transaction: " + t + (t == null ? "" : ", JEE status: " + TransactionStatus.statusToString(t.getStatus())));

            }
        }

        return ctx.proceed();
    }

    // Package protected -------------------------------------------------------------------------------------------------------------------

    // Protected ---------------------------------------------------------------------------------------------------------------------------

    // Private -----------------------------------------------------------------------------------------------------------------------------

    // Inner classes -----------------------------------------------------------------------------------------------------------------------
}



