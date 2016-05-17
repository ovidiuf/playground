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
public class Message extends Event {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private int counter;
    private String id;

    // Constructors ----------------------------------------------------------------------------------------------------

    public Message(int counter, String id) {

        this.counter = counter;
        this.id = id;
    }

    // Public ----------------------------------------------------------------------------------------------------------

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {

        if (id == null) {
            return false;
        }

        if (this == o) {
            return true;
        }

        if (!(o instanceof Message)) {
            return false;
        }

        Message that = (Message)o;
        return id.equals(that.getId());
    }

    @Override
    public int hashCode() {

        if (id == null) {
            return 0;
        }

        return 7 * id.hashCode() + 17;
    }

    @Override
    public String toString() {

        return counter + " " + id;

    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
