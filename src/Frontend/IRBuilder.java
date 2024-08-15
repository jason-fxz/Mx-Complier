package Frontend;

import org.antlr.v4.codegen.target.PHPTarget;

import AST.ASTVisitor;
import AST.Node.RootNode;
import AST.Node.def.ClassDefNode;
import AST.Node.def.FuncDefNode;
import AST.Node.def.VarDefNode;
import AST.Node.def.VarsDefNode;
import AST.Node.expr.ArrayExprNode;
import AST.Node.expr.ArrayInitNode;
import AST.Node.expr.AssignExprNode;
import AST.Node.expr.AtomExprNode;
import AST.Node.expr.BinaryExprNode;
import AST.Node.expr.BoolExprNode;
import AST.Node.expr.ConditionExprNode;
import AST.Node.expr.FmtStringExprNode;
import AST.Node.expr.FuncExprNode;
import AST.Node.expr.IntExprNode;
import AST.Node.expr.LeftSingleExprNode;
import AST.Node.expr.MemberExprNode;
import AST.Node.expr.NewArrayExprNode;
import AST.Node.expr.NewVarExprNode;
import AST.Node.expr.NullExprNode;
import AST.Node.expr.RightSingleExprNode;
import AST.Node.expr.StringExprNode;
import AST.Node.stmt.BlockStmtNode;
import AST.Node.stmt.BreakStmtNode;
import AST.Node.stmt.ContinueStmtNode;
import AST.Node.stmt.EmptyStmtNode;
import AST.Node.stmt.ExprStmtNode;
import AST.Node.stmt.ForStmtNode;
import AST.Node.stmt.IfStmtNode;
import AST.Node.stmt.ReturnStmtNode;
import AST.Node.stmt.VarDefStmtNode;
import AST.Node.stmt.WhileStmtNode;
import IR.IRhelper;
import IR.item.IRvar;
import IR.node.*;
import IR.node.def.*;
import IR.node.ins.*;
import IR.type.*;

public class IRBuilder implements ASTVisitor<IRhelper> {
    private IRblock curBlock;
    private IRFuncDef curFunc;
    private IRRoot root;


    public IRBuilder() {
        curBlock = null;
        curFunc = null;
        root = new IRRoot();
    }

    private void handleglobalVar(VarDefNode varDef) {
        // TODO
        
        if (varDef.init != null) {
            
        }
    }

    @Override
    public IRhelper visit(RootNode it) {
        // First Collect all the global variables

        
        it.Defs.forEach(sd -> {
            if (sd instanceof VarsDefNode) {
                ((VarsDefNode) sd).varDefs.forEach(vd -> {
                    handleglobalVar(vd);
                });
            }
        });

        it.Defs.forEach(sd -> {
            if (!(sd instanceof VarsDefNode)) {
                sd.accept(this);
            }
        });
        return new IRhelper();
    }

    @Override
    public IRhelper visit(ClassDefNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public IRhelper visit(FuncDefNode it) {
        IRFuncDef funcDef = new IRFuncDef(it.name, new IRType(it.type));
        curFunc = funcDef;
        curBlock = funcDef.entryBlock;
        it.params.forEach(p -> {
            funcDef.addParam(new IRvar(p));
        });
        for (var stmt : it.body.stmts) {
            stmt.accept(this);
        }
        curFunc = null;
        curBlock = null;
        return new IRhelper();
    }

    @Override
    public IRhelper visit(VarDefNode it) {
        // TODO

    }

    @Override
    public IRhelper visit(VarsDefNode it) {
        for (var varDef : it.varDefs) {
            varDef.accept(this);
        }
        return new IRhelper();
    }

    @Override
    public IRhelper visit(BinaryExprNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public IRhelper visit(LeftSingleExprNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public IRhelper visit(RightSingleExprNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public IRhelper visit(ConditionExprNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public IRhelper visit(AssignExprNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public IRhelper visit(AtomExprNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public IRhelper visit(MemberExprNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public IRhelper visit(FuncExprNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public IRhelper visit(NewVarExprNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public IRhelper visit(NewArrayExprNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public IRhelper visit(ArrayInitNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public IRhelper visit(ArrayExprNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public IRhelper visit(IntExprNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public IRhelper visit(BoolExprNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public IRhelper visit(StringExprNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public IRhelper visit(NullExprNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public IRhelper visit(FmtStringExprNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public IRhelper visit(BlockStmtNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public IRhelper visit(VarDefStmtNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public IRhelper visit(IfStmtNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public IRhelper visit(ForStmtNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public IRhelper visit(WhileStmtNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public IRhelper visit(ReturnStmtNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public IRhelper visit(BreakStmtNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public IRhelper visit(ContinueStmtNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public IRhelper visit(ExprStmtNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public IRhelper visit(EmptyStmtNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

}
