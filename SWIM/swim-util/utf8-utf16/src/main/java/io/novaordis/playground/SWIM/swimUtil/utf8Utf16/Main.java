package io.novaordis.playground.SWIM.swimUtil.utf8Utf16;

import swim.util.Output;

public class Main {

    public static void main(String[] args) {

        Output<byte[]> utf8ByteArrayOutput;

        //
        // U+0000 to U+007F Basic Multilingual Plane character representation - 1 UTF-8 byte
        //

        utf8ByteArrayOutput = Output.utf8Array();

        utf8ByteArrayOutput.write('A');

        byte[] bytes = utf8ByteArrayOutput.bind();

        System.out.println("array length: " + bytes.length + ", encoded format: 0x" + Integer.toHexString(0x000000FF & (int)bytes[0]));

        //
        // U+0080 to U+07FF Basic Multilingual Plane character representation - 2 UTF-8 bytes
        //

        utf8ByteArrayOutput = Output.utf8Array();

        utf8ByteArrayOutput.write("\u00DF".charAt(0));

        bytes = utf8ByteArrayOutput.bind();

        System.out.println("array length: " + bytes.length + ", encoded format: 0x" +
                Integer.toHexString(0x000000FF & (int)bytes[0]) +
                Integer.toHexString(0x000000FF & (int)bytes[1]));


        //
        // U+0800 to U+FFFF Basic Multilingual Plane character representation - 3 UTF-8 bytes
        //

        utf8ByteArrayOutput = Output.utf8Array();

        utf8ByteArrayOutput.write("\u6771".charAt(0));

        bytes = utf8ByteArrayOutput.bind();

        System.out.println("array length: " + bytes.length + ", encoded format: 0x" +
                Integer.toHexString(0x000000FF & (int)bytes[0]) +
                Integer.toHexString(0x000000FF & (int)bytes[1]) +
                Integer.toHexString(0x000000FF & (int)bytes[2]));


    }
}
