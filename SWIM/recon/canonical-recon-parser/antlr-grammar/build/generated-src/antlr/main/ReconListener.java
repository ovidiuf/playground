// Generated from Recon.g4 by ANTLR 4.7.1


package playground.swim.recon.antlr;


import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ReconParser}.
 */
public interface ReconListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ReconParser#record}.
	 * @param ctx the parse tree
	 */
	void enterRecord(ReconParser.RecordContext ctx);
	/**
	 * Exit a parse tree produced by {@link ReconParser#record}.
	 * @param ctx the parse tree
	 */
	void exitRecord(ReconParser.RecordContext ctx);
	/**
	 * Enter a parse tree produced by {@link ReconParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(ReconParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link ReconParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(ReconParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link ReconParser#slot}.
	 * @param ctx the parse tree
	 */
	void enterSlot(ReconParser.SlotContext ctx);
	/**
	 * Exit a parse tree produced by {@link ReconParser#slot}.
	 * @param ctx the parse tree
	 */
	void exitSlot(ReconParser.SlotContext ctx);
	/**
	 * Enter a parse tree produced by {@link ReconParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(ReconParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link ReconParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(ReconParser.ValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link ReconParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(ReconParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link ReconParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(ReconParser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link ReconParser#attr}.
	 * @param ctx the parse tree
	 */
	void enterAttr(ReconParser.AttrContext ctx);
	/**
	 * Exit a parse tree produced by {@link ReconParser#attr}.
	 * @param ctx the parse tree
	 */
	void exitAttr(ReconParser.AttrContext ctx);
}