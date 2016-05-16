/*
 * Copyright (c) 2016 Nova Ordis LLC
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

package io.novaordis.playground.wildfly.hornetq.ftf.common;

import javax.jms.Message;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 5/16/16
 */
public class MessageInfo {

    // Constants -------------------------------------------------------------------------------------------------------

    public static final MessageInfo NO_MORE_MESSAGES = new MessageInfo();

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private String messageId;
    private Exception exception;

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    /**
     * Initializes the MessageInfo instance with message data.
     */
    public void init(Message m) {

        try {
            this.messageId = m.getJMSMessageID();
        }
        catch(Exception e) {
            exception = e;
        }
    }

    /**
     * Initializes the MessageInfo instance with exception data
     */
    public void init(Exception e) {

        this.exception = e;
    }

    public String getMessageId() {
        return messageId;
    }

    public Exception getException() {
        return exception;
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
