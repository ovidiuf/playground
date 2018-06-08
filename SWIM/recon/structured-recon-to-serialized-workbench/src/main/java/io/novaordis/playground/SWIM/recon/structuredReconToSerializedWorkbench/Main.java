package io.novaordis.playground.SWIM.recon.structuredReconToSerializedWorkbench;

import recon.Attr;
import recon.Record;

public class Main {

    public static void main(String[] args) {

        Attr attr = Attr.of("color");

        final Record record = Record.of().slot("blah", "something");

        String serialized = record.toRecon();

        System.out.println(serialized);


    }
}
