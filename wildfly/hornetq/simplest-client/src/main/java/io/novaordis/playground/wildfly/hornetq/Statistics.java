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

package io.novaordis.playground.wildfly.hornetq;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Properties;

public class Statistics
{
    // Constants -------------------------------------------------------------------------------------------------------

    public static final Format CSV_TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");

    // Static ----------------------------------------------------------------------------------------------------------

    public static void record(long t0, long t1, int messageCounter, String messageID)
    {
        System.out.println(CSV_TIMESTAMP_FORMAT.format(t0) + ", " + (t1 - t0) + ", " + messageCounter + ", " + messageID);
    }

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
