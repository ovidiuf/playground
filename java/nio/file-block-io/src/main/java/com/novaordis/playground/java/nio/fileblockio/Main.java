package com.novaordis.playground.java.nio.fileblockio;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * @author Ovidiu Feodorov <ovidiu@novaordis.com>
 * @since 1/19/16
 */
public class Main {

    // Constants -------------------------------------------------------------------------------------------------------

    // Static ----------------------------------------------------------------------------------------------------------

    /**
     * @param args first argument is the file name.
     */
    public static void main(String[] args) throws Exception {

        if (args.length == 0) {
            throw new Exception("must specify a file name on the command line");
        }

        Path path = FileSystems.getDefault().getPath(args[0]);

        if (!path.toFile().canRead()) {
            throw new Exception("file " + path + " does not exist or cannot be read");
        }

        FileChannel fc = FileChannel.open(path);

        ByteBuffer buffer = ByteBuffer.allocate(10);

        int bytesRead = fc.read(buffer);

        System.out.println("bytes read: " + bytesRead);
    }

    // Attributes ------------------------------------------------------------------------------------------------------

    // Constructors ----------------------------------------------------------------------------------------------------

    // Public ----------------------------------------------------------------------------------------------------------

    // Package protected -----------------------------------------------------------------------------------------------

    // Protected -------------------------------------------------------------------------------------------------------

    // Private ---------------------------------------------------------------------------------------------------------

    // Inner classes ---------------------------------------------------------------------------------------------------

}
