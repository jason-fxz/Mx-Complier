package AST;

import AST.Node.def.*;
import AST.Node.stmt.*;
import AST.Node.expr.*;
import AST.Node.*;

public interface ASTVisitor {
    void visit(RootNode it);
    
    void visit(StmtNode it);
    void visit(BlockStmtNode it);
    void visit(VarDefStmtNode it);
    void visit(IfStmtNode it);
    

    
    void visit(ClassDefNode it);
    void visit(FuncDefNode it);
    void visit(VarDefNode it);
    

    
    void visit(returnStmtNode it);
    void visit(exprStmtNode it);

    void visit(assignExprNode it);
    void visit(binaryExprNode it);
    void visit(constExprNode it);
    void visit(cmpExprNode it);
    void visit(varExprNode it);
}