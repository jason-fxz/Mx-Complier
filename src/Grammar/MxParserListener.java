// Generated from ./src/Grammar/MxParser.g4 by ANTLR 4.13.1
package Grammar;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MxParser}.
 */
public interface MxParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MxParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(MxParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(MxParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#classDef}.
	 * @param ctx the parse tree
	 */
	void enterClassDef(MxParser.ClassDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#classDef}.
	 * @param ctx the parse tree
	 */
	void exitClassDef(MxParser.ClassDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#classConstruct}.
	 * @param ctx the parse tree
	 */
	void enterClassConstruct(MxParser.ClassConstructContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#classConstruct}.
	 * @param ctx the parse tree
	 */
	void exitClassConstruct(MxParser.ClassConstructContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#funcDef}.
	 * @param ctx the parse tree
	 */
	void enterFuncDef(MxParser.FuncDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#funcDef}.
	 * @param ctx the parse tree
	 */
	void exitFuncDef(MxParser.FuncDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#funcParamList}.
	 * @param ctx the parse tree
	 */
	void enterFuncParamList(MxParser.FuncParamListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#funcParamList}.
	 * @param ctx the parse tree
	 */
	void exitFuncParamList(MxParser.FuncParamListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#funcParam}.
	 * @param ctx the parse tree
	 */
	void enterFuncParam(MxParser.FuncParamContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#funcParam}.
	 * @param ctx the parse tree
	 */
	void exitFuncParam(MxParser.FuncParamContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#varDef}.
	 * @param ctx the parse tree
	 */
	void enterVarDef(MxParser.VarDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#varDef}.
	 * @param ctx the parse tree
	 */
	void exitVarDef(MxParser.VarDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#varConstruct}.
	 * @param ctx the parse tree
	 */
	void enterVarConstruct(MxParser.VarConstructContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#varConstruct}.
	 * @param ctx the parse tree
	 */
	void exitVarConstruct(MxParser.VarConstructContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#exprStmt}.
	 * @param ctx the parse tree
	 */
	void enterExprStmt(MxParser.ExprStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#exprStmt}.
	 * @param ctx the parse tree
	 */
	void exitExprStmt(MxParser.ExprStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#exprList}.
	 * @param ctx the parse tree
	 */
	void enterExprList(MxParser.ExprListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#exprList}.
	 * @param ctx the parse tree
	 */
	void exitExprList(MxParser.ExprListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#typeName}.
	 * @param ctx the parse tree
	 */
	void enterTypeName(MxParser.TypeNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#typeName}.
	 * @param ctx the parse tree
	 */
	void exitTypeName(MxParser.TypeNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(MxParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(MxParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#baseType}.
	 * @param ctx the parse tree
	 */
	void enterBaseType(MxParser.BaseTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#baseType}.
	 * @param ctx the parse tree
	 */
	void exitBaseType(MxParser.BaseTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#arrayUnit}.
	 * @param ctx the parse tree
	 */
	void enterArrayUnit(MxParser.ArrayUnitContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#arrayUnit}.
	 * @param ctx the parse tree
	 */
	void exitArrayUnit(MxParser.ArrayUnitContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ArrayInitExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterArrayInitExpr(MxParser.ArrayInitExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ArrayInitExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitArrayInitExpr(MxParser.ArrayInitExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BinaryExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterBinaryExpr(MxParser.BinaryExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BinaryExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitBinaryExpr(MxParser.BinaryExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AtomExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAtomExpr(MxParser.AtomExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AtomExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAtomExpr(MxParser.AtomExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NewVarExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNewVarExpr(MxParser.NewVarExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NewVarExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNewVarExpr(MxParser.NewVarExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FuncExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterFuncExpr(MxParser.FuncExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FuncExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitFuncExpr(MxParser.FuncExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AssignExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAssignExpr(MxParser.AssignExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AssignExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAssignExpr(MxParser.AssignExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ArrayExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterArrayExpr(MxParser.ArrayExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ArrayExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitArrayExpr(MxParser.ArrayExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NewArrayExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNewArrayExpr(MxParser.NewArrayExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NewArrayExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNewArrayExpr(MxParser.NewArrayExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MemberExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterMemberExpr(MxParser.MemberExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MemberExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitMemberExpr(MxParser.MemberExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code RightExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterRightExpr(MxParser.RightExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code RightExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitRightExpr(MxParser.RightExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FormatStringExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterFormatStringExpr(MxParser.FormatStringExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FormatStringExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitFormatStringExpr(MxParser.FormatStringExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ConditionExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterConditionExpr(MxParser.ConditionExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ConditionExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitConditionExpr(MxParser.ConditionExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LiterExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterLiterExpr(MxParser.LiterExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LiterExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitLiterExpr(MxParser.LiterExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ParenExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterParenExpr(MxParser.ParenExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ParenExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitParenExpr(MxParser.ParenExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LeftExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterLeftExpr(MxParser.LeftExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LeftExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitLeftExpr(MxParser.LeftExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#literalExpr}.
	 * @param ctx the parse tree
	 */
	void enterLiteralExpr(MxParser.LiteralExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#literalExpr}.
	 * @param ctx the parse tree
	 */
	void exitLiteralExpr(MxParser.LiteralExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#arrayInitial}.
	 * @param ctx the parse tree
	 */
	void enterArrayInitial(MxParser.ArrayInitialContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#arrayInitial}.
	 * @param ctx the parse tree
	 */
	void exitArrayInitial(MxParser.ArrayInitialContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#formatStrExpr}.
	 * @param ctx the parse tree
	 */
	void enterFormatStrExpr(MxParser.FormatStrExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#formatStrExpr}.
	 * @param ctx the parse tree
	 */
	void exitFormatStrExpr(MxParser.FormatStrExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterStmt(MxParser.StmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitStmt(MxParser.StmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#blockStmt}.
	 * @param ctx the parse tree
	 */
	void enterBlockStmt(MxParser.BlockStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#blockStmt}.
	 * @param ctx the parse tree
	 */
	void exitBlockStmt(MxParser.BlockStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#ifStmt}.
	 * @param ctx the parse tree
	 */
	void enterIfStmt(MxParser.IfStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#ifStmt}.
	 * @param ctx the parse tree
	 */
	void exitIfStmt(MxParser.IfStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#forStmt}.
	 * @param ctx the parse tree
	 */
	void enterForStmt(MxParser.ForStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#forStmt}.
	 * @param ctx the parse tree
	 */
	void exitForStmt(MxParser.ForStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#whileStmt}.
	 * @param ctx the parse tree
	 */
	void enterWhileStmt(MxParser.WhileStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#whileStmt}.
	 * @param ctx the parse tree
	 */
	void exitWhileStmt(MxParser.WhileStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#emptyStmt}.
	 * @param ctx the parse tree
	 */
	void enterEmptyStmt(MxParser.EmptyStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#emptyStmt}.
	 * @param ctx the parse tree
	 */
	void exitEmptyStmt(MxParser.EmptyStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#jumpStmt}.
	 * @param ctx the parse tree
	 */
	void enterJumpStmt(MxParser.JumpStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#jumpStmt}.
	 * @param ctx the parse tree
	 */
	void exitJumpStmt(MxParser.JumpStmtContext ctx);
}