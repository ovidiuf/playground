package io.novaordis.playground.SWIM.recon.serialized2structuredRecon;

import recon.Attr;
import recon.Item;
import recon.Record;
import recon.Slot;
import recon.Value;

import java.util.Arrays;
import java.util.Iterator;

public class Main {

    public static void main(String[] args) throws Exception {

        Configuration c = new Configuration(args);

        String serializedInput = c.getSerializedInput();

        String s = "input: ";

        if (serializedInput.contains("\n")) {

            s = "multi-line " + s + "\n";
        }

        s += serializedInput;

        System.out.println("\n" + s);

        Value value = Value.parseRecon(serializedInput);

        System.out.println("\nstructured RECON:\n");

        recursivelyDisplayOnMultipleLines(value, 4);

        String serialized = value.toRecon();

        System.out.println("\nre-serialized RECON: >" + serialized + "<\n");

    }

    private static void recursivelyDisplayOnMultipleLines(Item item, int indentation) {

        String leader = getLeader(indentation);

        if (item instanceof Record) {

            String type = item.getClass().getSimpleName();

            type = "RecordMap".equals(type) ? "Record" : type;

            System.out.println(leader + type + " { ");

            //
            // iterate
            //

            for(Item i: item) {

                recursivelyDisplayOnMultipleLines(i, indentation + 2);
            }

            System.out.println(leader + "}");
        }
        else {

            System.out.println(leader + toLiteral(item));
        }
    }

    /**
     * A "literal" representation of the Item.
     */
    private static String toLiteral(Item item) {

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

                sb.append(toLiteral(i));

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
                    append(toLiteral(attr.getValue())).
                    append(")");
        }
        else if (item.isSlot()) {

            Slot slot = (Slot)item;
            sb.append(slot.getClass().getSimpleName()).
                    append(" ").
                    append(toLiteral(slot.getKey())).
                    append(": ").
                    append(toLiteral(slot.getValue()));
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
}
