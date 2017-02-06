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

package io.novaordis.playground.jboss.activemq.util;

public class Configuration {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private Operation operation;

    private boolean messageCountRequired;

    private String jndiUrl;
    private String destinationName;
    private String connectionFactoryName;

    private int threadCount;
    private int messageCount;
    private int connectionCount = 1;

    private String username;
    private String password;

    private long sleepBetweenSendsMs = 0L;

    private boolean statsOnly;

    // Constructors ----------------------------------------------------------------------------------------------------

    public Configuration(String[] args) throws Exception
    {
        threadCount = 1;
        messageCount = -1;
        statsOnly = false;

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

    public Operation getOperation() {
        return operation;
    }

    /**
     * @return the time (in milliseconds) to sleep between sends. Negative or zero means don't sleep.
     */
    public long getSleepBetweenSendsMs() {
        return sleepBetweenSendsMs;
    }

    public int getConnectionCount() {
        return connectionCount;
    }

    public boolean isStatsOnly() {
        return statsOnly;
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    private void parseArguments(String[] args) throws Exception
    {
        for(int i = 0; i < args.length; i ++)
        {
            if (operation == null) {

                operation = Operation.valueOf(args[i]);

                if (Operation.send.equals(operation)) {
                    messageCountRequired = true;
                }
            }
            else if ("--destination".equals(args[i])) {

                destinationName = args[++i];
            }
            else if ("--jndi".equals(args[i])) {

                jndiUrl = args[++i];
            }
            else if ("--connection-factory".equals(args[i])) {

                connectionFactoryName = args[++i];
            }
            else if ("--username".equals(args[i])) {

                username = args[++i];
            }
            else if ("--password".equals(args[i])) {

                password = args[++i];
            }
            else if ("--messages".equals(args[i])) {

                messageCount = Integer.parseInt(args[++i]);
            }
            else if ("--threads".equals(args[i])) {

                threadCount = Integer.parseInt(args[++i]);
            } else if ("--connections".equals(args[i])) {

                connectionCount = Integer.parseInt(args[++i]);
            }
            else if ("--sleep-between-sends-ms".equals(args[i])) {

                sleepBetweenSendsMs = Long.parseLong(args[++i]);
            }
            else if ("--stats".equals(args[i])) {

                statsOnly = true;
            }
            else {

                throw new Exception("unknown argument: '" + args[i] + "'");
            }
        }
    }

    // Inner classes ---------------------------------------------------------------------------------------------------

}
