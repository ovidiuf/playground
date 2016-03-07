/*
 * Copyright (c) 2015 Nova Ordis LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.novaordis.playground.wildfly.hornetq.util;

import io.novaordis.playground.wildfly.hornetq.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Properties;

public class JNDI
{
    // Constants -------------------------------------------------------------------------------------------------------

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    // Static ----------------------------------------------------------------------------------------------------------

    public static Destination getDestination(String providerUrl, String destinationName) throws Exception
    {
        InitialContext ic = null;

        try
        {
            ic = new InitialContext(getProperties(providerUrl));

            log.info("looking up '" + destinationName + "' ...");
            //noinspection UnnecessaryLocalVariable
            Destination d = (Destination)ic.lookup(destinationName);

            return d;
        }
        finally
        {
            if (ic != null)
            {
                ic.close();
            }
        }
    }

    public static ConnectionFactory getConnectionFactory(String providerUrl, String connectionFactoryName)
        throws Exception
    {

        InitialContext ic = null;

        try
        {
            ic = new InitialContext(getProperties(providerUrl));

            log.info("looking up '" + connectionFactoryName + "' ...");
            //noinspection UnnecessaryLocalVariable
            ConnectionFactory cf = (ConnectionFactory)ic.lookup(connectionFactoryName);

            return cf;
        }
        finally
        {
            if (ic != null)
            {
                ic.close();
            }
        }
    }

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    private static Properties getProperties(String providerUrl)
    {
        Properties p = new Properties();

        p.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");

        if (!providerUrl.startsWith("remote://"))
        {
            providerUrl = "remote://" + providerUrl;
        }
        p.put(Context.PROVIDER_URL, providerUrl);
        return p;
    }

    // Inner classes ---------------------------------------------------------------------------------------------------

}
