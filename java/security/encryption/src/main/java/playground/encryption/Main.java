package playground.encryption;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * @author <a href="mailto:ovidiu@novaordis.com">Ovidiu Feodorov</a>
 *
 * Copyright 2010 Ovidiu Feodorov
 */
public class Main
{
    // Constants -----------------------------------------------------------------------------------

    // Static --------------------------------------------------------------------------------------

    public static void main(String[] args) throws Exception
    {
        if (args.length != 3)
        {
            displayUsage();
            System.exit(1);
        }

        String mode = args[0].toLowerCase();
        char[] password = args[1].toCharArray();

        byte[] salt =
            {
                (byte)0xde, (byte)0xad, (byte)0xbe, (byte)0xef,
                (byte)0xfe, (byte)0xed, (byte)0x01, (byte)0x99
            };

        int iterationCount = 17;

        ByteArrayInputStream input = new ByteArrayInputStream(args[2].getBytes());

        ByteArrayOutputStream output = new ByteArrayOutputStream();

        if ("encrypt".equals(mode))
        {
            ByteArrayOutputStream binaryEncrypted = new ByteArrayOutputStream();
            PBE.encrypt(input, binaryEncrypted, password, salt, iterationCount);
            // also base64 encode
            Base64.encode(new ByteArrayInputStream(binaryEncrypted.toByteArray()), output);
        }
        else if ("decrypt".equals(mode))
        {
            // we expect a base64 encoded input, first decode
            ByteArrayOutputStream fromBase64 = new ByteArrayOutputStream();
            Base64.decode(input, fromBase64);
            PBE.decrypt(new ByteArrayInputStream(fromBase64.toByteArray()),
                        output, password, salt, iterationCount);
        }
        else
        {
            displayUsage();
            System.exit(1);
        }

        // display result
        System.out.println(new String(output.toByteArray()));
    }

    // Attributes ----------------------------------------------------------------------------------

    // Constructors --------------------------------------------------------------------------------

    // Public --------------------------------------------------------------------------------------

    // Package protected ---------------------------------------------------------------------------

    // Protected -----------------------------------------------------------------------------------

    // Private -------------------------------------------------------------------------------------

    private static void displayUsage()
    {
        System.out.println(
            "Usage:\n\n" +
            "   run encrypt <password> <thing-to-encrypt>\n" +
            "   run decrypt <password> <thing-to-decrypt>");
    }

    // Inner classes -------------------------------------------------------------------------------

}
