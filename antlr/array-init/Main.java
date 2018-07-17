import java.io.FileInputStream;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class Main {

    public static void main(String[] args) throws Exception {

        FileInputStream fis = new FileInputStream(args[0]);

        ANTLRInputStream input = new ANTLRInputStream(fis);

        ArrayInitLexer lexer = new ArrayInitLexer(input);

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        ArrayInitParser parser = new ArrayInitParser(tokens);

        ParseTree tree = parser.init();

        ParseTreeWalker walker = new ParseTreeWalker();

        walker.walk(new Translator(), tree);

//        System.out.println(tree.toStringTree(parser));

        System.out.println();

    }

}