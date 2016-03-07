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

public class Configuration
{
    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private String jndiUrl;
    private String destinationName;
    private String connectionFactoryName;

    private int threadCount;
    private int messageCount;

    private String username;
    private String password;

    // Constructors ----------------------------------------------------------------------------------------------------

    public Configuration(String[] args, boolean messageCountRequired, boolean threadCountRequired) throws Exception
    {
        messageCount = -1;
        threadCount = -1;

        username = "jmsuser";
        password = "jmsuser123";

        parseArguments(args);

        if (jndiUrl == null)
        {
            throw new Exception("specify a JNDI URL name with --jndi");
        }

        if (destinationName == null)
        {
            throw new Exception("specify a destination name with --destination");
        }

        if (connectionFactoryName == null)
        {
            throw new Exception("specify a connection factory name with --connection-factory");
        }

        if (messageCountRequired && messageCount == -1)
        {
            throw new Exception("specify the number of messages to send with --messages");
        }

        if (threadCountRequired && threadCount == -1)
        {
            throw new Exception("specify the number of threads with --threads");
        }

    }

    // Public ----------------------------------------------------------------------------------------------------------

    public String getDestinationName()
    {
        return destinationName;
    }

    public String getJndiUrl()
    {
        return jndiUrl;
    }

    public String getConnectionFactoryName()
    {
        return connectionFactoryName;
    }

    /**
     * May return null.
     */
    public String getUserName()
    {
        return username;
    }

    /**
     * May return null.
     */
    public String getPassword()
    {
        return password;
    }

    public int getThreadCount()
    {
        return threadCount;
    }

    public int getMessageCount()
    {
        return messageCount;
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    private void parseArguments(String[] args) throws Exception
    {
        for(int i = 0; i < args.length; i ++)
        {
            if ("--destination".equals(args[i]))
            {
                destinationName = args[++i];
            }
            else if ("--jndi".equals(args[i]))
            {
                jndiUrl = args[++i];
            }
            else if ("--connection-factory".equals(args[i]))
            {
                connectionFactoryName = args[++i];
            }
            else if ("--messages".equals(args[i]))
            {
                messageCount = Integer.parseInt(args[++i]);
            }
            else if ("--threads".equals(args[i]))
            {
                threadCount = Integer.parseInt(args[++i]);
            }
            else
            {
                throw new Exception("unknown argument: '" + args[i] + "'");
            }

        }
    }

    // Inner classes ---------------------------------------------------------------------------------------------------

}
