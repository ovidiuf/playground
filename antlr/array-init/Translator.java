import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * @author Ovidiu Feodorov <ovidiu@swim.ai>
 * @since 7/12/18
 */
public class Translator extends ArrayInitBaseListener {

    @Override
    public void enterInit(ArrayInitParser.InitContext ctx) {

        //System.out.println("enter init");
        System.out.print("\"");
    }

    @Override
    public void exitInit(ArrayInitParser.InitContext ctx) {

        //System.out.println("exit init");
        System.out.print("\"");
    }

    @Override
    public void enterValue(ArrayInitParser.ValueContext ctx) {

        int value = Integer.valueOf(ctx.INT().getText());
        System.out.printf("\\u%04x", value);
    }

//    @Override
//    public void exitValue(ArrayInitParser.ValueContext ctx) {
//
//        System.out.println("exit value");
//    }

//    @Override
//    public void enterEveryRule(ParserRuleContext ctx) {
//
//        System.out.println("enter rule");
//    }
//
//    @Override
//    public void exitEveryRule(ParserRuleContext ctx) {
//
//        System.out.println("exit rule");
//    }

//    @Override
//    public void visitTerminal(TerminalNode node) {
//
//        System.out.println("visit terminal");
//    }
//
//    @Override public void visitErrorNode(ErrorNode node) {
//
//        System.out.println("visit error node");
//    }


}
