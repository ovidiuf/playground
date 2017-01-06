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

package io.novaordis.playground.http.server.http;

import java.io.ByteArrayOutputStream;

/**
 * A specialized ByteArrayOutputStream that gives access to the internal buffer. We need this for efficiency, as
 * we don't what to copy the buffer content every time we want to inspect it.
 *
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/6/17
 */
public class RequestBuffer extends ByteArrayOutputStream {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    public static boolean isDiscardableChar(char b) {

        return b == ' ' || b == '\n' || b == '\r' || b == '\t';
    }

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    /**
     * @return true if the buffer is empty or contains only white space and CR/LF (discardable content)
     */
    public boolean allDiscardableContent() {

        int size = size();

        if (size == 0) {

            return true;
        }

        for(int i = 0; i < size; i ++) {

            if (!isDiscardableChar((char)buf[i])) {

                return false;
            }
        }

        return true;
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
