package io.novaordis.playground.SWIM.recon.field;

import recon.Attr;
import recon.Slot;

public class Main {

    public static void main(String[] args) {

        Attr attr = Attr.of("color", "blue");
        Slot slot = Slot.of("color", "blue");

        System.out.println(attr.toRecon());
        System.out.println(slot.toRecon());
    }
}
