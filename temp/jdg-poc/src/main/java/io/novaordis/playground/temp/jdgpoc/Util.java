/*
 * Copyright (c) 2017 Nova Ordis LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.novaordis.playground.temp.jdgpoc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.transaction.TransactionManager;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 5/4/17
 */
public class Util {

    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(Util.class);

    // Static ----------------------------------------------------------------------------------------------------------

    public static TransactionManager getTransactionManager() throws ServletException {

        InitialContext ic = null;

        try {

            ic = new InitialContext();

            return (TransactionManager)ic.lookup("java:/TransactionManager");
        }
        catch(Exception e) {

            throw new ServletException(e);
        }
        finally {

            try {

                if (ic != null) {

                    ic.close();
                }
            }
            catch(Exception e) {

                log.warn("failed to close initial context", e);
            }
        }
    }

    public static boolean inTransaction() throws ServletException {


        TransactionManager tm = getTransactionManager();

        try {

            return tm.getTransaction() != null;
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
