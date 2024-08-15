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
import IR.item.IRLiteral;
import IR.item.IRvar;
import IR.node.*;
import IR.node.def.*;
import IR.node.ins.*;
import IR.type.*;
import Util.BuiltinElements;
import Util.IRLabeler;
import Util.error.InvalidTypeError;
import Util.info.ExprInfo;

public class IRBuilder implements ASTVisitor<IRhelper> {
    private IRblock curBlock;
    private IRFuncDef curFunc;
    private IRFuncDef gInit;
    private IRRoot root;

    public IRBuilder() {
        curBlock = null;
        curFunc = null;
        root = new IRRoot();
        gInit = new IRFuncDef("__global_init", new IRType("void"));
    }

    private void handleglobalVar(VarDefNode varDef) {
        IRvar var = new IRvar(varDef, true);
        root.gVars.add(new IRglobalVarDef(var));
        if (varDef.init != null) {
            var t = varDef.init.accept(this); // calc the init expr
            curBlock.addIns(new storeIns(t.exprVar, var)); // store the result to the global variable
        }
    }

    @Override
    public IRhelper visit(RootNode it) {
        // First Collect all the global variable
        curFunc = gInit;
        curBlock = gInit.entryBlock;
        it.Defs.forEach(sd -> {
            if (sd instanceof VarsDefNode) {
                ((VarsDefNode) sd).varDefs.forEach(vd -> {
                    handleglobalVar(vd);
                });
            }
        });
        gInit.entryBlock.setEndIns(new returnIns(new IRLiteral(IRType.IRvoidType, "void")));
        root.funcs.add(gInit);
        curFunc = null;
        curBlock = null;

        // Then handle the function or Class
        it.Defs.forEach(sd -> {
            if (!(sd instanceof VarsDefNode)) {
                sd.accept(this);
            }
        });
        return null;
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
            funcDef.addParam(new IRvar(p, false));
        });

        for (var stmt : it.body.stmts) {
            stmt.accept(this);
        }
        curFunc = null;
        curBlock = null;
        return null;
    }

    @Override
    public IRhelper visit(VarDefNode it) {
        IRvar var = new IRvar(new IRType(it.type), it.type.label);
        curFunc.entryBlock.addIns(new allocaIns(var)); // set the alloca instruction
        if (it.init != null) {
            IRhelper t = it.init.accept(this); // calc the init expr
            curFunc.entryBlock.addIns(new storeIns(t.exprVar, var)); // store the result to the variable
        }
        return null;
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

        String opstr = it.op.toIRIns();
        IRvar result = null;

        var lhsinfo = it.lhs.info;
        var rhsinfo = it.rhs.info;
        
        if (it.op.in("&&", "||")) {
            // Short-circuit evaluation
            
        } else {
            if (lhsinfo.isArray() || lhsinfo.isCustom()) {
                // only support == and !=  for array and class
                
                // TODO
            } else {
                // TODO
            }



        }


        return null; // TODO
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
