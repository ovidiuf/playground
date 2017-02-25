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

package io.novaordis.playground.json.jackson.streaming.json2java.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 2/25/17
 */
public class JsonField extends JsonValue {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private String name;
    private JsonValue value;

    // Constructors ----------------------------------------------------------------------------------------------------

    public JsonField(String name) {

        this.name = name;
    }

    // Public ----------------------------------------------------------------------------------------------------------

    public String getName() {

        return name;
    }

    /**
     * We just processed the FIELD_NAME, and the name has been set already. We load the value that comes after that
     */
    @Override
    public void load(JsonParser parser) throws Exception {

        JsonToken token = parser.nextToken();

        if (JsonToken.VALUE_NULL.equals(token)) {

            value = new JsonNull();
        }
        else if (JsonToken.VALUE_TRUE.equals(token)) {

            value = new JsonTrue();
        }
        else if (JsonToken.VALUE_FALSE.equals(token)) {

            value = new JsonFalse();
        }
        else if (JsonToken.VALUE_STRING.equals(token)) {

            String text = parser.getText();
            value = new JsonString(text);
        }
        else if (JsonToken.VALUE_NUMBER_INT.equals(token)) {

            int i = parser.getIntValue();
            value = new JsonInteger(i);
        }
        else if (JsonToken.VALUE_NUMBER_FLOAT.equals(token)) {

            float f = parser.getFloatValue();
            value = new JsonFloat(f);
        }
        else if (JsonToken.START_OBJECT.equals(token)) {

            value = new JsonObject();
            value.load(parser);
        }
        else if (JsonToken.START_ARRAY.equals(token)) {

            value = new JsonArray();
            value.load(parser);
        }
        else {

            throw new IllegalStateException("we don't know how to handle " + token);
        }
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
