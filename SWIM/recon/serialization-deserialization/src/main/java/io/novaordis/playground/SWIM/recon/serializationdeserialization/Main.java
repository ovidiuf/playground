package io.novaordis.playground.SWIM.recon.serializationdeserialization;

import recon.Value;

public class Main {

    public static void main(String[] args) {

        //
        // RECON text to and from RECON Objects
        //

        if (args.length == 0) {

            System.err.println("[error]: specify an argument that will be converted to RECON");
            return;
        }

        String arg = args[0];

        System.out.println("argument >" + arg + "<");

        Value v = Value.parseRecon(arg);

        System.out.println(v.getClass() + ": " + v);

        //
        // RECON text to and from RECON Objects
        //

        try {

            System.out.println("boolean value: " + v.booleanValue());
        }
        catch(Exception e) {

            System.out.println(v + " cannot be converted to boolean: " + e);
        }

        try {

            System.out.println("char value:    " + v.charValue());
        }
        catch(Exception e) {

            System.out.println(v + " cannot be converted to char: " + e);
        }

        try {

            System.out.println("int value:     " + v.intValue());
        }
        catch(Exception e) {

            System.out.println(v + " cannot be converted to int: " + e);
        }

        try {

            System.out.println("long value:    " + v.longValue());
        }
        catch(Exception e) {

            System.out.println(v + " cannot be converted to long: " + e);
        }

        try {

            System.out.println("float value:   " + v.floatValue());
        }
        catch(Exception e) {

            System.out.println(v + " cannot be converted to float: " + e);
        }

        try {

            System.out.println("double value:  " + v.doubleValue());
        }
        catch(Exception e) {

            System.out.println(v + " cannot be converted to double: " + e);
        }

        try {

            System.out.println("string value:  " + v.stringValue());
        }
        catch(Exception e) {

            System.out.println(v + " cannot be converted to string: " + e);
        }
    }
}
