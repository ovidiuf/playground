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

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/4/17
 */
public class HttpRequest {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private byte[] content;
    private String method;
    private String path;

    // Constructors ----------------------------------------------------------------------------------------------------

    /**
     * The header content, as read from the socket. Does not include the blank line.
     *
     * @exception InvalidHeaderException on faulty content that cannot be translated into a valid HTTP header
     */
    public HttpRequest(byte[] content) throws InvalidHeaderException {

        this.content = content;

        //
        // crude parsing, revisit
        //

        int pathIndex = -1;

        for(int i = 0; i < content.length; i ++) {

            if (content[i] == (byte)' ') {

                if (pathIndex == -1) {

                    method = new String(content, 0, i);
                    pathIndex = i + 1;
                    continue;
                }

                path = new String(content, pathIndex, i - pathIndex);
                break;
            }
        }
    }

    // Public ----------------------------------------------------------------------------------------------------------

    @SuppressWarnings("unused")
    public String getMethod() {

        return method;
    }

    public String getPath() {

        return path;
    }

    @Override
    public String toString() {

        if (content == null) {
            return "null content";
        }

        return new String(content);
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
