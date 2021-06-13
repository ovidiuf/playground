package playground.java.bytecode;

import java.io.*;

public class Main {

    public static void usage() throws Exception {
        String usageFileName = "usage.txt";
        InputStream is = Main.class.getClassLoader().getResourceAsStream(usageFileName);
        if (is == null) {
            throw new UserErrorException("in-line help file " + usageFileName + " not found");
        }
        try (BufferedReader r = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while((line = r.readLine()) != null) {
                System.out.println(line);
            }
        }
        catch (Exception e) {
            throw new UserErrorException("failed to read in-line help", e);
        }
    }

    public static void main(String[] args) {

        try {
            if (args.length == 0) {
                usage();
                System.exit(0);
            }

            String filename = args[0];
            try (DataInputStream dis = new DataInputStream(new FileInputStream(filename))) {
                int magic = dis.readInt();
                if (magic != 0xcafebabe) {
                    throw new UserErrorException(filename + " is not a valid Java class");
                }
                int minor = dis.readUnsignedShort();
                int major = dis.readUnsignedShort();
                System.out.println(filename + ": " + major + "." + minor);
            }
        }
        catch(Exception e) {
            System.err.println(("[error]: " + e.getMessage()));
            System.exit(1);
        }
    }
}
