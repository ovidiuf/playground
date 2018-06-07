package io.novaordis.playground.SWIM.recon.serialized2structuredRecon;

import recon.Attr;
import recon.Item;
import recon.Record;
import recon.Slot;
import recon.Value;
import recon.Number;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        String serializedInput = args[0];

        System.out.println("serialized input: >" + serializedInput + "<");

        Value value = Value.parseRecon(serializedInput);

        System.out.println("\nstructured RECON:\n");

        display(value, 4);

        String serialized = value.toRecon();

        System.out.println("\nre-serialized RECON: >" + serialized + "<\n");

    }

    private static void display(Item item, int indentation) {

        String leader = getLeader(indentation);

        if (item instanceof Record) {

            System.out.println(leader + item.getClass().getName() + ": ");

            //
            // iterate
            //

            for(Item i: (Record) item) {

                display(i, indentation + 2);
            }
        }
        else {

            if (item.isAbsent()) {

                System.out.println(leader + "ABSENT");
            }
            else if (item.isExtant()) {

                System.out.println(leader + "EXTANT");
            }
            else if (item.isBool()) {

                System.out.println(leader + item.getClass().getName() + ": " + item.stringValue());
            }
            else if (item.isText()) {

                System.out.println(leader + item.getClass().getName() + ": " + item.stringValue());
            }
            else if (item.isData()) {

                System.out.println(leader + item.getClass().getName() + ": " + item.stringValue());
            }
            else if (item.isNumber()) {

                Number n = (Number) item;

                System.out.println(leader + item.getClass().getName() + ": " + item.stringValue());
            }
            else if (item.isAttr()) {

                Attr attr = (Attr)item;

                System.out.println(leader + attr.getClass().getName() + ": " + attr.getName() + "=" + attr.getValue());

            }
            else if (item.isSlot()) {

                Slot slot = (Slot)item;

                System.out.println(leader + slot.getClass().getName() + ": " + slot.getKey() + "=" + slot.getValue());

            }
            else {

                throw new IllegalArgumentException("unknown type: " + item);
            }
        }
    }

    private static String getLeader(int indentationCharacters) {

        char[] c = new char[indentationCharacters];
        Arrays.fill(c, ' ');
        return new String(c);
    }
}
