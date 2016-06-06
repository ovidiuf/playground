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

package org.jgroups.protocols;

import org.jgroups.Event;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.stack.Protocol;

/**
 * A custom JGroups protocol that inspect messages and dumps their body content in the log.
 *
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 6/5/16
 */
public class INSPECT extends Protocol {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private boolean displayUpMessages = false;
    private boolean displayDownMessages = true;

    // Constructors ----------------------------------------------------------------------------------------------------

    // Protocol overrides ----------------------------------------------------------------------------------------------

    @Override
    public void init() {

        log.info(this + " initialized");
    }

    @Override
    public void start() {

        log.info(this + " started");
    }

    @Override
    public void stop() {

        log.info(this + " stopped");
    }

    @Override
    public void destroy() {

        log.info(this + " destroyed");
    }

    @Override
    public Object up(Event event) {

        int type = event.getType();

        if (displayUpMessages) {

            if (type == Event.MSG) {

                Message msg = (Message) event.getArg();

                if (msg != null) {

                    byte[] buffer = msg.getBuffer();

                    if (buffer != null) {
                        log.info(">>>" + new String(buffer));
                    }
                }
            }
        }
        return up_prot.up(event);
    }

    @Override
    public Object down(Event event) {

        int type = event.getType();

        if (displayDownMessages) {

            if (type == Event.MSG) {

                Message msg = (Message) event.getArg();

                if (msg != null) {

                    byte[] buffer = msg.getBuffer();

                    if (buffer != null) {
                        log.info(">>>" + new String(buffer));
                    }
                }
            }
        }

        return down_prot.down(event);
    }

    // Public ----------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {

        String name = "N/A";

        if (stack != null) {
            JChannel c = stack.getChannel();
            if (c != null) {
                name = c.getName();
            }
        }

        return "INSPECT[" + name + "]";
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
