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

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 2/25/17
 */
public class Main {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    public static void main(String[] args) throws Exception {

        //String fileName = "/root-is-an-object.json";
        String fileName = "/root-is-an-array.json";

        InputStream is = Main.class.getResourceAsStream(fileName);

        ObjectMapper om = new ObjectMapper();

        Object root = om.readValue(is, Object.class);

        traverse(root);
    }

    public static void traverse(Object o) {

        traverse(0, o);
        outln();
        outln();
    }

    private static void traverse(int depth, Object o) {

        if (o instanceof Map) {

            outln();

            Map m = (Map)o;

            for(Iterator i = m.keySet().iterator(); i.hasNext(); ) {

                Object k = i.next();

                out(indentation(depth));
                out(k + ": ");
                traverse(depth + 1, m.get(k));

                if (i.hasNext()) {
                    outln();
                }
            }
        }
        else if (o instanceof List) {

            outln();

            List l = (List)o;
            for(Iterator i = l.iterator(); i.hasNext(); ) {

                Object v = i.next();
                out(indentation(depth));
                out(v);

                if (i.hasNext()) {
                    outln();
                }
            }
        }
        else {

            out(o);
        }
    }

    private static String indentation(int depth) {

        String s = "";

        for(int i = 0; i < depth; i ++) {

            s += "    ";
        }

        return s;
    }

    private static void out(Object o) {

        System.out.print("" + o);
    }

    private static void outln(Object o) {

        System.out.println("" + o);
    }

    private static void outln() {

        System.out.println();
    }

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
