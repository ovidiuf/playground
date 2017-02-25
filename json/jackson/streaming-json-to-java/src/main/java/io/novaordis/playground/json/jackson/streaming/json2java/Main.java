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

package io.novaordis.playground.json.jackson.streaming.json2java;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import io.novaordis.playground.json.jackson.streaming.json2java.model.JsonArray;
import io.novaordis.playground.json.jackson.streaming.json2java.model.JsonObject;
import io.novaordis.playground.json.jackson.streaming.json2java.model.JsonValue;

import java.io.InputStream;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 2/25/17
 */
public class Main {

    // Constants -------------------------------------------------------------------------------------------------------

    public static final String INDENTATION_STEP = "    ";

    // Static ----------------------------------------------------------------------------------------------------------

    public static void main(String[] args) throws Exception {

        InputStream is = Main.class.getResourceAsStream("/example.json");

        JsonFactory f = new JsonFactory();

        //
        // configure it if necessary
        //

        f.enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES);

        JsonParser parser = f.createParser(is);

        //
        // stream parsing until the end of input is reached and we produce a JSON value, which can have other
        // values recursively embedded. See https://kb.novaordis.com/index.php/JSON_Concepts#Top-Level_Element
        //

        JsonValue content = getValue(parser);

        parser.close();

        System.out.println(content);
    }

    /**
     * May return null if we're at the end of the content.
     */
    public static JsonValue getValue(JsonParser parser) throws Exception {

        JsonValue result = null;

        if (parser.isClosed()) {

            return null;
        }

        JsonToken token = parser.nextToken();

        if (token == null) {

            return null;
        }

        if (JsonToken.START_OBJECT.equals(token)) {

            result = new JsonObject();

            result.load(parser);
        }
        else if (JsonToken.START_ARRAY.equals(token)) {

            result = new JsonArray();
        }
        else {

            throw new RuntimeException("DON'T KNOW TO HANDLE " + token);
        }

        return result;
    }

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
