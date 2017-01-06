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
 * @since 1/5/17
 */
public class HttpHeader {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    /**
     * @param to the index of the first element <b>to ignore</b>. It may extend beyond the array boundary and the
     *           method implementation should be prepared to deal with this.
     */
    public static HttpHeader parseHeader(byte[] content, int from, int to) throws InvalidHttpHeaderException {

        to = to >= content.length ? content.length : to;

        int fbi = -1;

        for(int i = from; i < to; i ++) {

            if (content[i] == ':') {

                fbi = i + 1;
                break;
            }
        }

        String fieldBody = null;
        String fieldName;

        if (fbi == -1) {

            fieldName = new String(content, from, to - from);
        }
        else {

            fieldName = new String(content, from, fbi - from - 1);
            fieldBody = new String(content, fbi, to - fbi).trim();
        }

        return new HttpHeader(fieldName, fieldBody);
    }

    // Attributes ------------------------------------------------------------------------------------------------------

    private String fieldName;
    private String fieldBody;

    // Constructors ----------------------------------------------------------------------------------------------------

    /**
     * @param fieldName must be non-null
     * @param fieldBody may be null
     */
    public HttpHeader(String fieldName, String fieldBody) {

        if (fieldName == null) {

            throw new IllegalArgumentException("null field name");
        }

        this.fieldName = fieldName;
        this.fieldBody = fieldBody;
    }

    // Public ----------------------------------------------------------------------------------------------------------

    public String getFieldName() {

        return fieldName;
    }

    /**
     * @return null if no separator was found in the header declaration. May return an empty string.
     */
    public String getFieldBody() {

        return fieldBody;
    }

    @Override
    public String toString() {

        return fieldName + (fieldBody == null ? "" : ":" + fieldBody);
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
