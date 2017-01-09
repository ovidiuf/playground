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

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the HTTP query section of the HTTP Request Path.
 *
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/9/17
 */
public class Query {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private Map<String, String> parameters;

    // Constructors ----------------------------------------------------------------------------------------------------

    /**
     * Empty query.
     */
    public Query() throws InvalidHttpRequestException {

        this(null);
    }

    /**
     * @param rawQuerySection the raw section of the path that follows '?' (not including '?'). Null is acceptable and
     *                        signifies "empty query"
     */
    public Query(String rawQuerySection) throws InvalidHttpRequestException {

        parameters = new HashMap<>();

        if (rawQuerySection != null) {
            parse(rawQuerySection);
        }
    }

    // Public ----------------------------------------------------------------------------------------------------------

    /**
     * @return the corresponding query parameter, if exists, or null otherwise.
     */
    public String getParameter(String parameterName) {

        return parameters.get(parameterName);
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    /**
     * @param rawQuerySection can't be null.
     */
    private void parse(String rawQuerySection) throws InvalidHttpRequestException {

        int i = 0;
        int pairStart = 0;
        for(; i < rawQuerySection.length(); i ++) {

            if (rawQuerySection.charAt(i) == '&') {

                parsePair(rawQuerySection, pairStart, i);
                pairStart = i + 1;
            }
        }

        //
        // parse the last pair
        //
        parsePair(rawQuerySection, pairStart, i);

    }

    private void parsePair(String s, int from, int to) throws InvalidHttpRequestException {

        String pair = s.substring(from, to);

        int i = pair.indexOf('=');

        if (i == -1) {

            throw new InvalidHttpRequestException("invalid HTTP request query section: \"" + s + "\"");
        }

        String name = pair.substring(0, i);
        String value = pair.substring(i + 1);
        parameters.put(name, value);
    }

    // Inner classes ---------------------------------------------------------------------------------------------------

}
