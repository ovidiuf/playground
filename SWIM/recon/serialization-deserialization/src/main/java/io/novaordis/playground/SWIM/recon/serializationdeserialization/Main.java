package io.novaordis.playground.SWIM.recon.serializationdeserialization;

import recon.Value;

public class Main {

    public static void main(String[] args) {

        Value v = Value.parseRecon("test");

        System.out.println(v);

        String s = v.toRecon();

        System.out.println(s);
    }
}
