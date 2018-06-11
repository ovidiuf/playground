package io.novaordis.playground.SWIM.recon.serialized2structuredRecon;

import recon.Attr;
import recon.Item;
import recon.Record;
import recon.Slot;
import recon.Value;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

public class Main {

    public static void main(String[] args) throws Exception {

        Configuration c = new Configuration(args);

        String serializedInput = c.getSerializedInput();

        displayInputForReference(serializedInput);

        Value reconValue = Value.parseRecon(serializedInput);

        System.out.println("\nstructured RECON:\n");

        recursivelyDisplayOfStructuredReconOnMultipleLines(reconValue, 4);

//        String serialized = reconValue.toRecon();
//
//        System.out.println("\nre-serialized RECON: >" + serialized + "<\n");

        Gson gson = new Gson();

        Object o = gson.fromJson(serializedInput, Object.class);

        System.out.println("\nJava from JSON via Gson:\n");

        recursivelyDisplayOfJavaParsedByGson(o, 4);

    }

    private static void displayInputForReference(String serializedInput) {

        String s = "input: ";

        if (serializedInput.contains("\n")) {

            s = "multi-line " + s + "\n";
        }

        s += serializedInput;

        System.out.println("\n" + s);
    }

    private static void recursivelyDisplayOfStructuredReconOnMultipleLines(Item item, int indentation) {

        String leader = getLeader(indentation);

        if (item instanceof Record) {

            String type = item.getClass().getSimpleName();

            type = "RecordMap".equals(type) ? "Record" : type;

            System.out.println(leader + type + " { ");

            //
            // iterate
            //

            for(Item i: item) {

                recursivelyDisplayOfStructuredReconOnMultipleLines(i, indentation + 2);
            }

            System.out.println(leader + "}");
        }
        else {

            System.out.println(leader + reconItemToLiteral(item));
        }
    }

    /**
     * A "literal" representation of the Item.
     */
    private static String reconItemToLiteral(Item item) {

        StringBuilder sb = new StringBuilder();

        if (item instanceof Record) {

            String type = item.getClass().getSimpleName();

            type = "RecordMap".equals(type) ? "Record" : type;

            sb.append(type).append(" {");

            //
            // iterate
            //

            for(Iterator<Item> it = item.iterator(); it.hasNext(); ) {

                Item i = it.next();

                sb.append(reconItemToLiteral(i));

                if (it.hasNext()) {

                    sb.append(", ");
                }
            }

            sb.append("}");
        }
        else if (item.isAbsent()) {

            sb.append("ABSENT");
        }
        else if (item.isExtant()) {

            sb.append("EXTANT");
        }
        else if (item.isText()) {

            //
            // unlike booleans, data and numbers, we want to be able to represent leading or trailing whitespaces
            //

            sb.append(item.getClass().getSimpleName()).
                    append(" '").
                    append(item.stringValue()).
                    append("'");
        }
        else if (item.isBool() || item.isData() || item.isNumber()) {

            sb.append(item.getClass().getSimpleName()).
                    append(" ").
                    append(item.stringValue());
        }
        else if (item.isAttr()) {

            Attr attr = (Attr)item;
            sb.append(attr.getClass().getSimpleName()).
                    append(" ").
                    append(attr.getName()).
                    append(" (").
                    append(reconItemToLiteral(attr.getValue())).
                    append(")");
        }
        else if (item.isSlot()) {

            Slot slot = (Slot)item;
            sb.append(slot.getClass().getSimpleName()).
                    append(" ").
                    append(reconItemToLiteral(slot.getKey())).
                    append(": ").
                    append(reconItemToLiteral(slot.getValue()));
        }
        else {

            throw new IllegalArgumentException("unknown type: " + item);
        }

        return sb.toString();
    }

    private static String getLeader(int indentationCharacters) {

        char[] c = new char[indentationCharacters];
        Arrays.fill(c, ' ');
        return new String(c);
    }

    private static void recursivelyDisplayOfJavaParsedByGson(Object o, int indentation) {

        String leader = getLeader(indentation);

        if (o instanceof Map) {

            Map m = (Map)o;

            System.out.println(leader + "Map { ");

            //
            // iterate
            //

            for(Iterator i = m.keySet().iterator(); i.hasNext(); ) {

                Object key = i.next();

                System.out.print(leader + "  " + key + ": " + javaObjectParsedFromJSONtoLiteral(m.get(key)));
                System.out.println();
            }

            System.out.println(leader + "}");
        }
        else {

            System.out.println(leader + javaObjectParsedFromJSONtoLiteral(o));
        }

        System.out.println();
    }

    /**
     * A "literal" representation of a Java Object parsed from JSON
     */
    private static String javaObjectParsedFromJSONtoLiteral(Object o) {

        StringBuilder sb = new StringBuilder();

        if (o instanceof List) {

            List list = (List)o;

            sb.append("[");

            for(Iterator i = list.iterator(); i.hasNext(); ) {

                Object li = i.next();

                sb.append(javaObjectParsedFromJSONtoLiteral(li));

                if (i.hasNext()) {

                    sb.append(", ");
                }
            }

            sb.append("]");
        }
        else if (o instanceof Map) {

            throw new RuntimeException("NYE: Map");

        }
        else if (o == null) {

            sb.append("null");
        }
        else if (o instanceof String) {

            sb.append("String: '").
                    append(o.toString()).append("'");
        }

        else {

            sb.append(o.getClass().getSimpleName()).
                    append(": ").
                    append(o.toString());
        }

        return sb.toString();
    }
}
