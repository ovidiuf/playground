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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 2/25/17
 */
public class JsonArray extends JsonValue {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private List<JsonValue> values;

    // Constructors ----------------------------------------------------------------------------------------------------

    public JsonArray() {

        this.values = new ArrayList<>();
    }

    // Public ----------------------------------------------------------------------------------------------------------

    /**
     * The parser just detected a JsonToken.START_ARRAY. We expect a list of values, that may be empty.
     */
    @Override
    public void load(JsonParser parser) throws Exception {

        while(true) {

            JsonToken token = parser.nextToken();

            if (JsonToken.END_ARRAY.equals(token)) {

                //
                // we're done loading this Array
                //
                return;
            }
            if (JsonToken.VALUE_NULL.equals(token)) {

                values.add(new JsonNull());
            }
            else if (JsonToken.VALUE_TRUE.equals(token)) {

                values.add(new JsonTrue());
            }
            else if (JsonToken.VALUE_FALSE.equals(token)) {

                values.add(new JsonFalse());
            }
            else if (JsonToken.VALUE_STRING.equals(token)) {

                String text = parser.getText();
                values.add(new JsonString(text));
            }
            else if (JsonToken.VALUE_NUMBER_INT.equals(token)) {

                int i = parser.getIntValue();
                values.add(new JsonInteger(i));
            }
            else if (JsonToken.VALUE_NUMBER_FLOAT.equals(token)) {

                float f = parser.getFloatValue();
                values.add(new JsonFloat(f));
            }
            else if (JsonToken.START_OBJECT.equals(token)) {

                JsonObject value = new JsonObject();
                value.load(parser);
                values.add(value);
            }
            else if (JsonToken.START_ARRAY.equals(token)) {

                JsonArray value = new JsonArray();
                value.load(parser);
                values.add(value);
            }
            else {

                throw new IllegalStateException(this + " does not expect " + token + " while loading");
            }
        }
    }

    @Override
    public void printJson(String indentation, boolean indentFirstLine) {

        if (indentFirstLine) {

            System.out.println(indentation);
        }

        System.out.println("[");

        for(Iterator<JsonValue> i = values.iterator(); i.hasNext(); ) {

            JsonValue f = i.next();

            f.printJson(indentation + "    ", true);

            if (i.hasNext()) {

                System.out.print(",");
            }

            System.out.println();
        }

        System.out.println(indentation + "]");
    }

    @Override
    public String toString() {

        return "[" + values.size() + " elements]";
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
