package playground.swim.recon;

import java.io.File;

/**
 * @author Ovidiu Feodorov <ovidiu@swim.ai>
 * @since 7/17/18
 */
class Configuration {

    private String fileName;
    private File file;

    private boolean graphic;

    Configuration(String[] args) throws Exception {

        for(int i = 0; i < args.length; i ++) {

            if ("-g".equals(args[i]) || "--graphic".equals(args[i])) {

                graphic = true;
            }
            else if (fileName == null) {

                fileName = args[i];
            }
        }

        sanityCheck();
    }

    /**
     * May return null. If it returns a non-null value, the value is valid (the file exists and can be read).
     */
    File getFile() {

        return file;
    }

    boolean isGraphic() {

        return graphic;
    }

    private void sanityCheck() throws UserErrorException {

        if (fileName != null) {

            //
            // we make sure the file exists and can be read
            //

            file = new File(fileName);

            if (!file.isFile()) {

                throw new UserErrorException("file " + file + " does not exist");
            }

            if (!file.canRead()) {

                throw new UserErrorException("file " + file + " cannot be read");
            }

        }
    }
}
