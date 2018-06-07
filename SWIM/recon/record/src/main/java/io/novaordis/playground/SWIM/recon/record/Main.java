package io.novaordis.playground.SWIM.recon.record;

import recon.Bool;
import recon.Data;
import recon.Item;
import recon.Record;
import recon.Text;
import recon.Value;

public class Main {

    public static void main(String[] args) {

        System.out.println("");
        System.out.println("#");
        System.out.println("# Building an empty Record instance with Record.empty(), then using the Builder pattern to fill it");
        System.out.println("#");
        System.out.println("");

        Record record = Record.empty();

        record.
                item(Bool.TRUE).
                item(Text.valueOf("something")).
                item(Value.of("something else")).
                item(Data.wrap("blue".getBytes())).
                item(Value.of(10)).
                item(Value.of(1.1f)).
                item(Value.of(1.1d));

        String reconText = record.toRecon();

        System.out.println(reconText);

        System.out.println("");
        System.out.println("#");
        System.out.println("# Converting the Record RECON text back to structured RECON");
        System.out.println("#");
        System.out.println("");

        Record record2 = (Record)Value.parseRecon(reconText);

        for (Item item : record2) {

            System.out.println(item.getClass() + ": " + item);
        }

        System.out.println("");
        System.out.println("#");
        System.out.println("# Building a Record instance with Record.of(...)");
        System.out.println("#");
        System.out.println("");

        Record record3 = Record.of(true, "some string", 10, 1.1);

        System.out.println(record3.toRecon());

//        System.out.println("");
//        System.out.println("#");
//        System.out.println("# Text special cases");
//        System.out.println("#");
//        System.out.println("");
//        Value v = Value.parseRecon("\\\"1.1\\\"");
//        System.out.println(v.getClass() + ": " + ((Text)v).stringValue());

    }
}
