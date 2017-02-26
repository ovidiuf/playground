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

package io.novaordis.playground.json.jackson.simpledatabinding.json2java;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 2/25/17
 */
public class Main {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    public static void main(String[] args) throws Exception {

        InputStream is = Main.class.getResourceAsStream("/example.json");

        ObjectMapper om = new ObjectMapper();

        JsonNode root = om.readTree(is);

        traverse(0, true, root);

        //
        // interesting Object access methods
        //

        System.out.println("object-example.object-element: " + root.get("object-example").get("object-element"));
        System.out.println("array-example[1]: " + root.get("array-example").get(1));


    }



    public static void traverse(int depth, boolean indent, JsonNode node) {

        JsonNodeType type = node.getNodeType();

        if (JsonNodeType.OBJECT.equals(type)) {

            System.out.println("\n" + indentation(depth) + "{");

            for(Iterator<Map.Entry<String, JsonNode>> i = node.fields(); i.hasNext(); ) {

                Map.Entry<String, JsonNode> field = i.next();
                String fieldName = field.getKey();
                JsonNode value = field.getValue();

                System.out.print(indentation(depth + 1) + "\"" + fieldName + "\": ");

                traverse(depth + 1, false, value);

                if (i.hasNext()) {

                    System.out.print(",");
                }

                System.out.println();
            }

            System.out.print(indentation(depth) + "}");
        }
        else if (JsonNodeType.ARRAY.equals(type)) {

            System.out.println("\n" + indentation(depth) + "[");

            for(Iterator<JsonNode> i = node.iterator(); i.hasNext(); ) {

                traverse(depth + 1, true, i.next());

                if (i.hasNext()) {

                    System.out.print(",");
                }

                System.out.println();
            }

            System.out.print(indentation(depth) + "]");
        }
        else {

            displayLeaf(depth, indent, node);
        }

        if (depth == 0) {

            System.out.println();
        }
    }

    public static void displayLeaf(int depth, boolean indent, JsonNode node) {

        JsonNodeType type = node.getNodeType();

        if (JsonNodeType.OBJECT.equals(type) || JsonNodeType.ARRAY.equals(type)) {

            throw new IllegalArgumentException(node + " not a leaf");
        }

        if (indent) {

            System.out.print(indentation(depth));
        }

        if (JsonNodeType.NULL.equals(type)) {

            System.out.print("null");
        }
        else if (JsonNodeType.BOOLEAN.equals(type)) {

            System.out.print(node.booleanValue());
        }
        else if (JsonNodeType.NUMBER.equals(type)) {

            System.out.print(node.numberValue());
        }
        else if (JsonNodeType.STRING.equals(type)) {

            System.out.print("\"" + node.textValue() + "\"");
        }
        else {

            throw new RuntimeException("NOT YET IMPLEMENTED");
        }
    }

    public static String indentation(int depth) {

        String s = "";

        for(int i = 0; i < depth; i ++) {

            s += "    ";
        }

        return s;
    }

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
