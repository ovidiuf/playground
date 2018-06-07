package io.novaordis.playground.SWIM.recon.serialized2structuredRecon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Ovidiu Feodorov <ovidiu@swim.ai>
 * @since 6/7/18
 */
class Configuration {

    private String serializedInput;
    private File file;

    Configuration(String[] args) {

        for(int i = 0; i < args.length; i ++) {

            String arg = args[i];

            if ("-f".equals(arg)) {

                file = new File(args[++i]);
                break;
            }
            else if (serializedInput == null) {

                serializedInput = arg;
            }
        }
    }

    /**
     * Either the first argument or the content of the file specified as -f
     */
    String getSerializedInput() throws IOException {

        if (serializedInput != null) {

            return serializedInput;
        }

        if (file != null) {

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {

                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = br.readLine()) != null) {

                    sb.append(line).append('\n');
                }

                serializedInput = sb.toString();
                return serializedInput;
            }
        }

        throw new IllegalStateException("no input, nor file");
    }
}
