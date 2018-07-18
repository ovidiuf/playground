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
    private boolean help;

    Configuration(String[] args) throws Exception {

        for(int i = 0; i < args.length; i ++) {

            if ("-h".equals(args[i]) || "--help".equals(args[i])) {

                help = true;

                //
                // if help requested, shortcut processing and get out without a sanity check
                //

                return;
            }
            else if ("-g".equals(args[i]) || "--graphic".equals(args[i])) {

                graphic = true;
            }
            else if (args[i].startsWith("-")){

                throw new UserErrorException("unknown command line option: '" + args[i] + "'");
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

    boolean isHelp() {

        return help;
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
