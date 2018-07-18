package playground.swim.recon;

import playground.swim.recon.antlr.ReconLexer;
import playground.swim.recon.antlr.ReconParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
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

    public static void main(String[] args) throws Exception {

        try {

            Configuration c = new Configuration(args);

            File file = c.getFile();

            //
            // for a graphic representation, we delegate to ANTLR's test rig
            //

            if (c.isGraphic()) {

                List<String> testRigArgs = new ArrayList<>(Arrays.asList("Recon", "record", "-gui"));

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

}
