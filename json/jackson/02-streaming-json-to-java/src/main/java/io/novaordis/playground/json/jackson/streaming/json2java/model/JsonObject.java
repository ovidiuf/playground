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
public class JsonObject extends JsonValue {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    // Attributes ------------------------------------------------------------------------------------------------------

    private List<JsonField> fields;

    // Constructors ----------------------------------------------------------------------------------------------------

    public JsonObject() {

        fields = new ArrayList<>();
    }

    // Public ----------------------------------------------------------------------------------------------------------

    /**
     * The parser just detected a JsonToken.START_OBJECT. We expect a list of fields, that may be empty.
     */
    @Override
    public void load(JsonParser parser) throws Exception {

        while(true) {

            JsonToken token = parser.nextToken();

            if (JsonToken.END_OBJECT.equals(token)) {

                //
                // we're done loading this Object
                //
                return;
            }
            else if (JsonToken.FIELD_NAME.equals(token)) {

                //
                // we identified a field
                //

                String name = parser.getText();

                JsonField field = new JsonField(name);

                field.load(parser);

                fields.add(field);
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

        System.out.println("{");

        for(Iterator<JsonField> i = fields.iterator(); i.hasNext(); ) {

            JsonField f = i.next();

            f.printJson(indentation + "    ", true);

            if (i.hasNext()) {

                System.out.print(",");
            }

            System.out.println();
        }

        System.out.print(indentation + "}");
    }

    @Override
    public String toString() {

        return "{" + fields.size() + " elements}";
    }

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
