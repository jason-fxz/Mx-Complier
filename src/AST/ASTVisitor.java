package AST;

import AST.Node.def.*;
import AST.Node.stmt.*;
import AST.Node.expr.*;


import AST.Node.*;

public interface ASTVisitor<T> {
    public T visit(RootNode it);
    
    public T visit(ClassDefNode it);
    public T visit(FuncDefNode it);
    public T visit(VarDefNode it);
    public T visit(VarsDefNode it);
    
    
    public T visit(BinaryExprNode it);
    public T visit(LeftSingleExprNode it);
    public T visit(RightSingleExprNode it);
    public T visit(ConditionExprNode it);
    public T visit(AssignExprNode it);
    public T visit(AtomExprNode it);
    public T visit(MemberExprNode it);
    public T visit(FuncExprNode it);
    public T visit(NewVarExprNode it);
    public T visit(NewArrayExprNode it);
    public T visit(ArrayInitNode it);
    public T visit(ArrayExprNode it);
    public T visit(IntExprNode it);
    public T visit(BoolExprNode it);
    public T visit(StringExprNode it);
    public T visit(NullExprNode it);
    public T visit(FmtStringExprNode it);



    public T visit(BlockStmtNode it);
    public T visit(VarDefStmtNode it);
    public T visit(IfStmtNode it);
    public T visit(ForStmtNode it);
    public T visit(WhileStmtNode it);
    public T visit(ReturnStmtNode it);
    public T visit(BreakStmtNode it);
    public T visit(ContinueStmtNode it);
    public T visit(ExprStmtNode it);
    public T visit(EmptyStmtNode it);


    
    
}