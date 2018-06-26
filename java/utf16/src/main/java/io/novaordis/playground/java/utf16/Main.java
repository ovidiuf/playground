package io.novaordis.playground.java.utf16;

public class Main {

    public static void main(String[] args) {

        String s;

        //
        // U+0000 to U+007F Basic Multilingual Plane character representation
        //

        s = "\u0041";

        System.out.println(s);


        //
        // U+0080 to U+07FF Basic Multilingual Plane character representation
        //

        s = "\u00DF";

        System.out.println(s);

        //
        // U+0800 to U+FFFF Basic Multilingual Plane character representation
        //

        s = "\u6771";

        System.out.println(s);

        //
        // Supplemental character representation
        //

        int codePoint = Character.toCodePoint((char)0xD801, (char)0xDC00);

        System.out.println("Supplementary character code point: U+" + Integer.toHexString(codePoint));

        char[] surrogateCodeUnits = Character.toChars(0x10400);

        System.out.println("Surrogate Code Units: " +
                Integer.toHexString((int)surrogateCodeUnits[0]) + " " +
                Integer.toHexString((int)surrogateCodeUnits[1]));

    }
}
