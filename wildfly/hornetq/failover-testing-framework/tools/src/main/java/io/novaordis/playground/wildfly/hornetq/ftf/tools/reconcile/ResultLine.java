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

package io.novaordis.playground.wildfly.hornetq.ftf.tools.reconcile;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 5/16/16
 */
public class ResultLine {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private String line;
    private Event event;

    // Constructors ----------------------------------------------------------------------------------------------------

    public ResultLine(Header header, String line) {
        this.line = line;

        String[] tok = line.split(",");

        String counter = tok[0].trim();
        String id = tok[1].trim();
        String fault = tok[2].trim();

        if (fault.length() != 0) {
            event = new Failure();
        }
        else {
            int cnt = Integer.parseInt(counter);
            event = new Message(cnt, id);
        }
    }

    // Public ----------------------------------------------------------------------------------------------------------

    public Event toEvent() {
        return event;
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
