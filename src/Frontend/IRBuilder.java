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
import IR.node.ins.phiIns.phiItem;
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
        IRvar res = null;
        var lhsinfo = it.lhs.info;
        var rhsinfo = it.rhs.info;

        if (it.op.in("&&", "||")) {
            // Short-circuit evaluation
            IRvar lhsvar = (IRvar)it.lhs.accept(this).exprVar;
            IRblock land_rhs = curFunc.newBlock(IRLabeler.getIdLabel("land.rhs"));
            IRblock land_end = curFunc.newBlock(IRLabeler.getIdLabel("land.end"));
            land_rhs.setEndIns(new jumpIns(land_end.getLabel()));
            res = new IRvar(IRType.IRBoolType, IRLabeler.getIdLabel(opstr));

            IRblock lastcur = curBlock;
            curBlock = land_rhs;
            IRvar rhsvar = (IRvar)it.rhs.accept(this).exprVar;

            if (it.op.equals("&&")) {
                lastcur.setEndIns(new branchIns(lhsvar, land_rhs.getLabel(), land_end.getLabel()));
                land_end.addIns(new phiIns(res,
                        new phiItem(new IRLiteral(IRType.IRBoolType, "false"), lastcur.getLabel()),
                        new phiItem(rhsvar, land_rhs.getLabel())));
            } else {
                lastcur.setEndIns(new branchIns(lhsvar, land_end.getLabel(), land_rhs.getLabel()));
                land_end.addIns(new phiIns(res,
                        new phiItem(new IRLiteral(IRType.IRBoolType, "ture"), lastcur.getLabel()),
                        new phiItem(rhsvar, land_rhs.getLabel())));
            }
            curBlock = land_end;

        } else {
            IRvar lhsvar = (IRvar)it.lhs.accept(this).exprVar;
            IRvar rhsvar = (IRvar)it.rhs.accept(this).exprVar;
            if (lhsinfo.isArray() || lhsinfo.isCustom()) {
                // only support == and != for array and class
                res = new IRvar(IRType.IRBoolType, IRLabeler.getIdLabel(opstr));
                if (it.op.in("==", "!=")) {
                    curBlock.addIns(new icmpIns(res, opstr, lhsvar, rhsvar));
                }
            } else if (lhsinfo.equals(BuiltinElements.stringType)) {
                if (it.op.equals("+")) {
                    // strcat TODO
                } else if (it.op.in("==", "!=", "<", ">", "<=", ">=")) {
                    // strcmp TODO
                }
                throw new UnsupportedOperationException("WTF? String binary");
            } else if (lhsinfo.equals(BuiltinElements.intType)) {
                if (it.op.in("==", "!=", "<", ">", "<=", ">=")) {
                    // camp
                    res = new IRvar(IRType.IRBoolType, IRLabeler.getIdLabel(opstr));
                    curBlock.addIns(new icmpIns(res, opstr, lhsvar, rhsvar));
                } else {
                    // arith
                    res = new IRvar(IRType.IRIntType, IRLabeler.getIdLabel(opstr));
                    curBlock.addIns(new arithIns(res, opstr, lhsvar, rhsvar));
                }
            } else if (lhsinfo.equals(BuiltinElements.boolType)) {
                if (it.op.in("==", "!=")) {
                    res = new IRvar(IRType.IRBoolType, IRLabeler.getIdLabel(opstr));
                    curBlock.addIns(new icmpIns(res, opstr, lhsvar, rhsvar));
                }
            }

        }
        if (res == null) {
            throw new UnsupportedOperationException("res == null !!! visit(BinaryExprNode it) ");
        }

        return new IRhelper(res); // TODO
    }

    @Override
    public IRhelper visit(LeftSingleExprNode it) {
        IRhelper rhs = it.rhs.accept(this), helper = new IRhelper();
        var rhsinfo = it.rhs.info;
        IRvar res = null;
        if (rhsinfo.equals(BuiltinElements.intType)) {
            if (it.op.in("++")) { // LValue
                res = new IRvar(IRType.IRIntType, IRLabeler.getIdLabel("Lselfinc"));
                curBlock.addIns(new arithIns(res, "add", rhs.exprVar, new IRLiteral("1")));
                curBlock.addIns(new storeIns(res, rhs.exprAddr));
                helper.exprAddr = rhs.exprAddr;
            } else if (it.op.in("--")) {
                res = new IRvar(IRType.IRIntType, IRLabeler.getIdLabel("Lselfdec"));
                curBlock.addIns(new arithIns(res, "sub", rhs.exprVar, new IRLiteral("1")));
                curBlock.addIns(new storeIns(res, rhs.exprAddr));
                helper.exprAddr = rhs.exprAddr;
            } else if (it.op.in("-")) {
                res = new IRvar(IRType.IRIntType, IRLabeler.getIdLabel("neg"));
                curBlock.addIns(new arithIns(res, "sub", new IRLiteral(IRType.IRIntType, "0"), helper.exprVar));
            } else if (it.op.in("~")) {
                res = new IRvar(IRType.IRIntType, IRLabeler.getIdLabel("not"));
                curBlock.addIns(new arithIns(res, "xor", helper.exprVar, new IRLiteral("-1")));
            } else throw new UnsupportedOperationException("WTF? int LeftSingleExprNode");
        } else if (rhsinfo.equals(BuiltinElements.boolType)) {
            if (it.op.in("!")) {
                res = new IRvar(IRType.IRBoolType, IRLabeler.getIdLabel("not"));
                curBlock.addIns(new arithIns(res, "xor",  helper.exprVar, new IRLiteral("true")));
            } else throw new UnsupportedOperationException("WTF? bool LeftSingleExprNode");
        } else throw new UnsupportedOperationException("WTF?? LeftSingleExprNode");
        
        helper.exprVar = res;
        return helper;
    }

    @Override
    public IRhelper visit(RightSingleExprNode it) {
        IRhelper lhs = it.lhs.accept(this), helper = new IRhelper();
        var lhsinfo = it.lhs.info;
        IRvar res = null;

        if (lhsinfo.equals(BuiltinElements.intType)) {
            if (it.op.in("++")) {
                res = new IRvar(IRType.IRIntType, IRLabeler.getIdLabel("Rselfinc"));
                curBlock.addIns(new arithIns(res, "add", lhs.exprVar, new IRLiteral("1")));
            } else if (it.op.in("--")) {
                res = new IRvar(IRType.IRIntType, IRLabeler.getIdLabel("Rselfdec"));
                curBlock.addIns(new arithIns(res, "sub", lhs.exprVar, new IRLiteral("1")));                
            } else throw new UnsupportedOperationException("WTF? int RightSingleExprNode");
        } else throw new UnsupportedOperationException("WTF?? RightSingleExprNode");
        helper.exprVar = res;
        return helper;
    }

    @Override
    public IRhelper visit(ConditionExprNode it) {
        IRvar cond = (IRvar)it.cond.accept(this).exprVar;
        IRvar truevar = (IRvar)it.thenExpr.accept(this).exprVar;
        IRvar falsevar = (IRvar)it.elseExpr.accept(this).exprVar;
        IRvar res = new IRvar(truevar.type, IRLabeler.getIdLabel("cond"));
        curBlock.addIns(new selectIns(res, cond, truevar, falsevar));
        return new IRhelper(res);
    }

    @Override
    public IRhelper visit(AssignExprNode it) {
        IRhelper lhs = it.lhs.accept(this), rhs = it.rhs.accept(this), helper = new IRhelper();
        curBlock.addIns(new storeIns(rhs.exprVar, rhs.exprAddr));
        helper.exprVar = rhs.exprVar;
        helper.exprAddr = lhs.exprAddr;
        return helper;
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
        return new IRhelper(new IRLiteral(IRType.IRIntType, String.valueOf(it.value)));
    }

    @Override
    public IRhelper visit(BoolExprNode it) {
        return new IRhelper(new IRLiteral(IRType.IRBoolType, String.valueOf(it.value)));
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
