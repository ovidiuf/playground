package io.novaordis.playground;

import java.io.FileWriter;
import java.io.RandomAccessFile;
import java.util.Date;

public class Main {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    public static void main(String[] args) throws Exception {

        if (args.length == 0) {

            truncateFile();

        }
        else if ("write".equals(args[0])) {

            writeFile();
        }
        else {

            throw new Exception("unknown command " + args[0]);
        }
    }
    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    private static void writeFile() throws Exception {

        FileWriter fw = new FileWriter("./test.txt");

        while(true) {

            System.out.print(".");
            fw.write(new Date().toString() + "\n");
            fw.flush();
            Thread.sleep(1000L);
        }

    }

    private static void truncateFile() throws Exception {


        RandomAccessFile raf = new RandomAccessFile("./test.txt", "rws");

        long pointer = raf.getFilePointer();

        System.out.println("pointer: " + pointer);

        raf.setLength(0L);
        raf.close();

        System.out.println("truncated");

    }


    // Inner classes ---------------------------------------------------------------------------------------------------


}
