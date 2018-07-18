package playground.swim.recon;

import playground.swim.recon.antlr.ReconLexer;
import playground.swim.recon.antlr.ReconParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 * @author Ovidiu Feodorov <ovidiu@swim.ai>
 * @since 7/17/18
 */
public class Main {

    private static final String HELP_FILE_NAME = "Help.txt";

    public static void main(String[] args) throws Exception {

        try {

            Configuration c = new Configuration(args);

            if (c.isHelp()) {

                displayHelp();
                System.exit(0);
            }

            File file = c.getFile();

            //
            // for a graphic representation, we delegate to ANTLR's test rig
            //

            if (c.isGraphic()) {

                List<String> testRigArgs = new ArrayList<>(Arrays.asList("playground.swim.recon.antlr.Recon", "record", "-gui"));

                if (file != null) {

                    testRigArgs.add(file.toString());
                }

                String[] testRigArgArray = new String[testRigArgs.size()];
                org.antlr.v4.gui.TestRig.main(testRigArgs.toArray(testRigArgArray));

                return;

            }

            InputStream is;

            if (c.getFile() != null) {

                is = new FileInputStream(c.getFile());
            }
            else {

                is = System.in;
            }

            ANTLRInputStream input = new ANTLRInputStream(is);

            ReconLexer lexer = new ReconLexer(input);

            CommonTokenStream tokenStream = new CommonTokenStream(lexer);

            ReconParser parser = new ReconParser(tokenStream);

            ParseTree tree = parser.record();

            System.out.println(tree);

//        EvalVisitor eval = new EvalVisitor();
//        eval.visit(tree);

        }
        catch(UserErrorException e) {

            System.err.println("[error]: " + e.getMessage());
        }
    }

    private static void displayHelp() throws UserErrorException {

        InputStream is = Main.class.getClassLoader().getResourceAsStream("HELP.txt");

        if (is == null) {

            throw new UserErrorException("in-line help content not found, was the program installed correctly?");
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String line;

        try {

            while ((line = br.readLine()) != null) {

                System.out.println(line);
            }

        }
        catch(IOException e) {

            throw new UserErrorException("failed to read " + HELP_FILE_NAME, e);
        }
    }
}
