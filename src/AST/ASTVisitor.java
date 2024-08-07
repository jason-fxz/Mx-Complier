package AST;

import AST.Node.def.*;
import AST.Node.stmt.*;
import AST.Node.expr.*;


import AST.Node.*;

public interface ASTVisitor {
    void visit(RootNode it);
    
    void visit(ClassDefNode it);
    void visit(FuncDefNode it);
    void visit(VarDefNode it);
    
    
    void visit(BinaryExprNode it);
    void visit(LeftSingleExprNode it);
    void visit(RightSingleExprNode it);
    void visit(ConditionExprNode it);
    void visit(AssignExprNode it);
    void visit(AtomExprNode it);
    void visit(MemberExprNode it);
    void visit(FuncExprNode it);
    void visit(NewExprNode it);
    void visit(ArrayInitNode it);
    void visit(ArrayExprNode it);
    void visit(IntExprNode it);
    void visit(BoolExprNode it);
    void visit(StringExprNode it);
    void visit(FmtStringExprNode it);



    
    
    void visit(BlockStmtNode it);
    void visit(VarDefStmtNode it);
    void visit(IfStmtNode it);
    void visit(ForStmtNode it);
    void visit(WhileStmtNode it);
    void visit(ReturnStmtNode it);
    void visit(BreakStmtNode it);
    void visit(ContinueStmtNode it);
    void visit(ExprStmtNode it);


    
    
}