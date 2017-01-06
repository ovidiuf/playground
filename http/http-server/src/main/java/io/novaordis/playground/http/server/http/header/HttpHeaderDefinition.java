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

package io.novaordis.playground.http.server.http.header;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/6/17
 */
public interface HttpHeaderDefinition {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    /**
     * @return null if no known header definition can be identified. Must behave this way to support non-standard
     * headers.
     */
    static HttpHeaderDefinition fromString(String s) {

        if (s == null) {

            return null;
        }

        for(HttpRequestHeader h: HttpRequestHeader.values()) {

            if (h.getCanonicalFieldName().equalsIgnoreCase(s)) {
                return h;
            }
        }

        for(HttpResponseHeader h: HttpResponseHeader.values()) {

            if (h.getCanonicalFieldName().equalsIgnoreCase(s)) {
                return h;
            }
        }

        for(HttpGeneralHeader h: HttpGeneralHeader.values()) {

            if (h.getCanonicalFieldName().equalsIgnoreCase(s)) {
                return h;
            }
        }

        for(HttpEntityHeader h: HttpEntityHeader.values()) {

            if (h.getCanonicalFieldName().equalsIgnoreCase(s)) {
                return h;
            }
        }


        return null;

    }

    // Public ----------------------------------------------------------------------------------------------------------

    /**
     * @return the canonical value for the corresponding HTTP header field name.
     */
    String getCanonicalFieldName();

}
