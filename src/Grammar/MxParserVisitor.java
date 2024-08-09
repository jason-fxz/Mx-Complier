// Generated from ./src/Grammar/MxParser.g4 by ANTLR 4.13.1
package Grammar;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MxParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MxParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MxParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(MxParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#classDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDef(MxParser.ClassDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#classConstruct}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassConstruct(MxParser.ClassConstructContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#funcDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncDef(MxParser.FuncDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#funcParamList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncParamList(MxParser.FuncParamListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#funcParam}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncParam(MxParser.FuncParamContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#varDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDef(MxParser.VarDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#varConstruct}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarConstruct(MxParser.VarConstructContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#exprStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprStmt(MxParser.ExprStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#exprList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprList(MxParser.ExprListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#typeName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeName(MxParser.TypeNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#arrayUnit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayUnit(MxParser.ArrayUnitContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(MxParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#baseType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBaseType(MxParser.BaseTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArrayInitExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayInitExpr(MxParser.ArrayInitExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BinaryExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryExpr(MxParser.BinaryExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AtomExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtomExpr(MxParser.AtomExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NewVarExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewVarExpr(MxParser.NewVarExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FuncExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncExpr(MxParser.FuncExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AssignExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignExpr(MxParser.AssignExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArrayExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayExpr(MxParser.ArrayExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NewArrayExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewArrayExpr(MxParser.NewArrayExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MemberExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemberExpr(MxParser.MemberExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code RightExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRightExpr(MxParser.RightExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FormatStringExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormatStringExpr(MxParser.FormatStringExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ConditionExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditionExpr(MxParser.ConditionExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LiterExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiterExpr(MxParser.LiterExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ParenExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenExpr(MxParser.ParenExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LeftExpr}
	 * labeled alternative in {@link MxParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLeftExpr(MxParser.LeftExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#literalExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteralExpr(MxParser.LiteralExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#arrayInitial}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayInitial(MxParser.ArrayInitialContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#formatStrExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormatStrExpr(MxParser.FormatStrExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmt(MxParser.StmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#varDefStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDefStmt(MxParser.VarDefStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#blockStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockStmt(MxParser.BlockStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#ifStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStmt(MxParser.IfStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#forStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStmt(MxParser.ForStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#whileStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStmt(MxParser.WhileStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#emptyStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEmptyStmt(MxParser.EmptyStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#jumpStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJumpStmt(MxParser.JumpStmtContext ctx);
}