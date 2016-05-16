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

package io.novaordis.playground.wildfly.hornetq.ftf.common;

import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicLong;

public class MessageReceivedReporter extends TimerTask
{
    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private long reportedSoFar;
    private AtomicLong messageReceived;

    // Constructors ----------------------------------------------------------------------------------------------------

    public MessageReceivedReporter(AtomicLong messageReceived)
    {
        this.messageReceived = messageReceived;
    }

    // TimerTask implementation ---------------------------------------------------------------------------------------

    @Override
    public void run()
    {
        long total = messageReceived.get();
        System.out.println(total - reportedSoFar);
        reportedSoFar = total;
    }

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
