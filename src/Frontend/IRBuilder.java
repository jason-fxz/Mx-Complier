package Frontend;


import AST.ASTVisitor;
import AST.Node.RootNode;
import AST.Node.def.*;
import AST.Node.expr.*;
import AST.Node.stmt.*;
import IR.IRhelper;
import IR.item.IRLiteral;
import IR.item.IRitem;
import IR.item.IRvar;
import IR.node.*;
import IR.node.def.*;
import IR.node.ins.*;
import IR.node.ins.phiIns.phiItem;
import IR.type.*;
import Util.BuiltinElements;
import Util.IRLabeler;
import java.util.ArrayList;

public class IRBuilder implements ASTVisitor<IRhelper> {
    private IRblock curBlock;
    private IRFuncDef curFunc;
    private IRFuncDef gInit;
    private IRRoot root;
    private String curClassName;
    private ArrayList<IRvar> curClassMembers;
    private ArrayList<String> LoopStack_break;
    private ArrayList<String> LoopStack_continue;

    public IRBuilder() {
        curBlock = null;
        curFunc = null;
        curClassMembers = null;
        root = new IRRoot();
        LoopStack_break = new ArrayList<>();
        LoopStack_continue = new ArrayList<>();
        gInit = new IRFuncDef("@__mx_global_init", new IRType("void"));
    }
    
    public IRRoot getRoot() {
        return root;
    }

    private void handleglobalVar(VarDefNode varDef) {
        IRvar var = new IRvar(varDef, true);
        // in fact global variable is a global pointer
        root.gVars.add(new IRglobalVarDef(var));
        if (varDef.init != null) {
            var t = varDef.init.accept(this); // calc the init expr
            curBlock.addIns(new storeIns(t.exprVar, var)); // store the result to the global variable
        }
    }

    @Override
    public IRhelper visit(RootNode it) {
        // Builtin Functions Declaration
        root.builtinfuncs.add(new IRFuncDec("@print", new IRType("void"), new IRType("ptr")));
        root.builtinfuncs.add(new IRFuncDec("@println", new IRType("void"), new IRType("ptr")));
        root.builtinfuncs.add(new IRFuncDec("@printInt", new IRType("void"), new IRType("i32")));
        root.builtinfuncs.add(new IRFuncDec("@printlnInt", new IRType("void"), new IRType("i32")));
        root.builtinfuncs.add(new IRFuncDec("@getString", new IRType("ptr")));
        root.builtinfuncs.add(new IRFuncDec("@getInt", new IRType("i32")));
        root.builtinfuncs.add(new IRFuncDec("@toString", new IRType("ptr"), new IRType("i32")));

        root.builtinfuncs.add(new IRFuncDec("@__mx_string_concat", new IRType("ptr"), new IRType("ptr"), new IRType("ptr")));
        root.builtinfuncs.add(new IRFuncDec("@__mx_string_compare", new IRType("i32"), new IRType("ptr"), new IRType("ptr")));
        root.builtinfuncs.add(new IRFuncDec("@string.length", new IRType("i32"), new IRType("ptr")));
        root.builtinfuncs.add(new IRFuncDec("@string.substring", new IRType("ptr"), new IRType("ptr"), new IRType("i32"), new IRType("i32")));
        root.builtinfuncs.add(new IRFuncDec("@string.parseInt", new IRType("i32"), new IRType("ptr")));
        root.builtinfuncs.add(new IRFuncDec("@string.ord", new IRType("i32"), new IRType("ptr"), new IRType("i32")));

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
        curClassName = it.name;
        IRStructType classType = new IRStructType("class." + it.name);
        curClassMembers = new ArrayList<>();

        it.classInfo.GetMembers().forEach((name, info) -> {
            classType.AddMember(new IRType(info));
            curClassMembers.add(new IRvar("%this.m." + name));
        });

        root.gStrust.add(new IRStructDef(classType));

        for (var func : it.funcDefs) {
            func.accept(this);
        }
        if (it.constructor != null) {
            it.constructor.accept(this);
        }

        curClassName = null;
        return null;
    }

    @Override
    public IRhelper visit(FuncDefNode it) {
        IRFuncDef funcDef = new IRFuncDef("@" + (curClassName == null ? it.name : curClassName + "." + it.name),
        new IRType(it.type));
        root.funcs.add(funcDef);
        curFunc = funcDef;
        curBlock = funcDef.entryBlock;
        if (it.name.equals("main")) {
            curBlock.addIns(new callIns("__mx_global_init"));
        }
        
        if (curClassName != null) {
            funcDef.addParam(new IRvar(IRType.IRPtrType, "%this"));
            
            for (int i = 0; i < curClassMembers.size(); ++i) {
                IRvar tmpvar = curClassMembers.get(i);
                funcDef.entryBlock.addIns(new getelementptr(tmpvar, new IRvar("%this"), "%class." + curClassName, 0, i));
            }
        }

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
            curBlock.addIns(new storeIns(t.exprVar, var)); // store the result to the variable
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
        // var rhsinfo = it.rhs.info;

        if (it.op.in("&&", "||")) {
            // Short-circuit evaluation
            IRvar lhsvar = (IRvar) it.lhs.accept(this).exprVar;
            IRblock land_rhs = curFunc.newBlock(IRLabeler.getIdLabel("land.rhs"));
            IRblock land_end = curFunc.newBlock(IRLabeler.getIdLabel("land.end"));

            res = new IRvar(IRType.IRBoolType, IRLabeler.getIdLabel("%" + opstr));

            IRblock lastcur = curBlock; // land.lhs done
            curBlock = land_rhs;
            IRvar rhsvar = (IRvar) it.rhs.accept(this).exprVar;
            curBlock.setEndIns(new jumpIns(land_end.getLabel()));

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
            IRitem lhsvar = it.lhs.accept(this).exprVar;
            IRitem rhsvar = it.rhs.accept(this).exprVar;
            if (lhsinfo.isArray() || lhsinfo.isCustom()) {
                // only support == and != for array and class
                res = new IRvar(IRType.IRBoolType, IRLabeler.getIdLabel("%" + opstr));
                if (it.op.in("==", "!=")) {
                    curBlock.addIns(new icmpIns(res, opstr, lhsvar, rhsvar));
                }
            } else if (lhsinfo.equals(BuiltinElements.stringType)) {
                if (it.op.equals("+")) {
                    // strcat my builtin.c 
                    // char *__mx_string_concat(char *s1, char *s2)
                    res = new IRvar(IRType.IRPtrType, IRLabeler.getIdLabel("%str.concat"));
                    curBlock.addIns(new callIns(res, "__mx_string_concat", lhsvar, rhsvar));
                } else if (it.op.in("==", "!=", "<", ">", "<=", ">=")) {
                    // strcmp my builtin.c
                    // int __mx_string_compare(char *s1, char *s2)
                    IRvar tmpcmp = new IRvar(IRType.IRIntType, IRLabeler.getIdLabel("%str.cmp"));
                    res = new IRvar(IRType.IRBoolType, IRLabeler.getIdLabel("%str.cmp." + opstr));
                    curBlock.addIns(new callIns(tmpcmp, "__mx_string_compare", lhsvar, rhsvar));
                    curBlock.addIns(new icmpIns(res, opstr, tmpcmp, new IRLiteral("0")));
                } else throw new UnsupportedOperationException("WTF? String binary: " + it.op);
            } else if (lhsinfo.equals(BuiltinElements.intType)) {
                if (it.op.in("==", "!=", "<", ">", "<=", ">=")) {
                    // camp
                    res = new IRvar(IRType.IRBoolType, IRLabeler.getIdLabel("%" + opstr));
                    curBlock.addIns(new icmpIns(res, opstr, lhsvar, rhsvar));
                } else {
                    // arith
                    res = new IRvar(IRType.IRIntType, IRLabeler.getIdLabel("%" + opstr));
                    curBlock.addIns(new arithIns(res, opstr, lhsvar, rhsvar));
                }
            } else if (lhsinfo.equals(BuiltinElements.boolType)) {
                if (it.op.in("==", "!=")) {
                    res = new IRvar(IRType.IRBoolType, IRLabeler.getIdLabel("%" + opstr));
                    curBlock.addIns(new icmpIns(res, opstr, lhsvar, rhsvar));
                }
            }

        }
        if (res == null) {
            throw new UnsupportedOperationException("res == null !!! visit(BinaryExprNode it) ");
        }

        return new IRhelper(res);
    }

    @Override
    public IRhelper visit(LeftSingleExprNode it) {
        IRhelper rhs = it.rhs.accept(this), helper = new IRhelper();
        var rhsinfo = it.rhs.info;
        IRvar res = null;
        if (rhsinfo.equals(BuiltinElements.intType)) {
            if (it.op.in("++")) { // LValue
                res = new IRvar(IRType.IRIntType, IRLabeler.getIdLabel("%Lselfinc"));
                curBlock.addIns(new arithIns(res, "add", rhs.exprVar, new IRLiteral("1")));
                curBlock.addIns(new storeIns(res, rhs.exprAddr));
                helper.exprAddr = rhs.exprAddr;
            } else if (it.op.in("--")) {
                res = new IRvar(IRType.IRIntType, IRLabeler.getIdLabel("%Lselfdec"));
                curBlock.addIns(new arithIns(res, "sub", rhs.exprVar, new IRLiteral("1")));
                curBlock.addIns(new storeIns(res, rhs.exprAddr));
                helper.exprAddr = rhs.exprAddr;
            } else if (it.op.in("-")) {
                res = new IRvar(IRType.IRIntType, IRLabeler.getIdLabel("%neg"));
                curBlock.addIns(new arithIns(res, "sub", new IRLiteral(IRType.IRIntType, "0"), helper.exprVar));
            } else if (it.op.in("~")) {
                res = new IRvar(IRType.IRIntType, IRLabeler.getIdLabel("%not"));
                curBlock.addIns(new arithIns(res, "xor", helper.exprVar, new IRLiteral("-1")));
            } else
                throw new UnsupportedOperationException("WTF? int LeftSingleExprNode");
        } else if (rhsinfo.equals(BuiltinElements.boolType)) {
            if (it.op.in("!")) {
                res = new IRvar(IRType.IRBoolType, IRLabeler.getIdLabel("%not"));
                curBlock.addIns(new arithIns(res, "xor", helper.exprVar, new IRLiteral("true")));
            } else
                throw new UnsupportedOperationException("WTF? bool LeftSingleExprNode");
        } else
            throw new UnsupportedOperationException("WTF?? LeftSingleExprNode");

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
                res = new IRvar(IRType.IRIntType, IRLabeler.getIdLabel("%Rselfinc"));
                curBlock.addIns(new arithIns(res, "add", lhs.exprVar, new IRLiteral("1")));
            } else if (it.op.in("--")) {
                res = new IRvar(IRType.IRIntType, IRLabeler.getIdLabel("%Rselfdec"));
                curBlock.addIns(new arithIns(res, "sub", lhs.exprVar, new IRLiteral("1")));
            } else
                throw new UnsupportedOperationException("WTF? int RightSingleExprNode");
        } else
            throw new UnsupportedOperationException("WTF?? RightSingleExprNode");
        helper.exprVar = res;
        return helper;
    }

    @Override
    public IRhelper visit(ConditionExprNode it) {
        IRvar cond = (IRvar) it.cond.accept(this).exprVar;
        IRvar truevar = (IRvar) it.thenExpr.accept(this).exprVar;
        IRvar falsevar = (IRvar) it.elseExpr.accept(this).exprVar;
        IRvar res = new IRvar(truevar.type, IRLabeler.getIdLabel("cond"));
        curBlock.addIns(new selectIns(res, cond, truevar, falsevar));
        return new IRhelper(res);
    }

    @Override
    public IRhelper visit(AssignExprNode it) {
        IRhelper lhs = it.lhs.accept(this), rhs = it.rhs.accept(this), helper = new IRhelper();
        curBlock.addIns(new storeIns(rhs.exprVar, lhs.exprAddr));
        helper.exprVar = rhs.exprVar;
        helper.exprAddr = lhs.exprAddr;
        return helper;
    }

    @Override
    public IRhelper visit(AtomExprNode it) {
        if (it.info.isFunc) {
            IRhelper helper = new IRhelper(it.info.label);
            if (it.info.label.contains(".")) {
                helper.funthis = new IRvar("%this");
            }
            return helper;
        } else {
            // if (it.info.label.startsWith("!this!")) {
            //     IRvar tmpthis = new IRvar(IRType.IRPtrType, IRLabeler.getIdLabel("%atom.this"));
            //     curBlock.addIns(new loadIns(tmpthis, new IRvar("%this.addr")));
            //     IRvar varaddr = new IRvar(IRLabeler.getIdLabel("%member." + it.name + ".addr"));
            //     IRvar tmpvar = new IRvar(new IRType(it.info), IRLabeler.getIdLabel("%member." + it.name));
                
            //     int elementId = Integer.parseInt(it.info.label.substring(6));
            //     curBlock.addIns(new getelementptr(varaddr, tmpthis, "%class." + curClassName, 0, elementId));
            //     curBlock.addIns(new loadIns(tmpvar, varaddr));

            //     return new IRhelper(tmpvar, varaddr);
            // } else {

                // a tmp var load from 
                IRvar tmpvar = new IRvar(new IRType(it.info), IRLabeler.getIdLabel("%atom." + it.name));
                IRvar varaddr = new IRvar(it.info.label);
                curBlock.addIns(new loadIns(tmpvar, varaddr));
                return new IRhelper(tmpvar, varaddr);
            // }
            
        }
    }

    @Override
    public IRhelper visit(MemberExprNode it) {
        IRhelper obj = it.object.accept(this), helper = new IRhelper();

        if (it.info.isFunc) {
            helper.funName = it.info.label;
            helper.funthis = (IRvar)obj.exprVar;
        } else {
            IRvar tmpvar = new IRvar(new IRType(it.info), IRLabeler.getIdLabel("%" + it.member));
            IRvar varaddr = new IRvar(IRLabeler.getIdLabel("%" + it.member + ".addr"));
            
            int elementId = Integer.parseInt(it.info.label);
            curBlock.addIns(new getelementptr(varaddr, obj.exprVar, "%class." + it.object.info.typeName, 0, elementId));
            curBlock.addIns(new loadIns(tmpvar, varaddr));
            helper.exprVar = tmpvar;
            helper.exprAddr = varaddr;
        }
        return helper;
    }

    @Override
    public IRhelper visit(FuncExprNode it) {
        IRhelper func = it.func.accept(this);
        ArrayList<IRitem> args = new ArrayList<>();
        if (func.funthis != null) {
            args.add(func.funthis);
        }
        it.args.forEach(arg -> {
            args.add(arg.accept(this).exprVar);
        });
        IRhelper helper = new IRhelper();
        if (it.func.info.isVoid) {
            curBlock.addIns(new callIns(func.funName, args));
        } else {
            IRvar res = new IRvar(new IRType(it.info), IRLabeler.getIdLabel("%call." + func.funName));
            curBlock.addIns(new callIns(res, func.funName, args));
            helper.exprVar = res; 
        }

        return helper;
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
        IRvar var = new IRvar(IRType.IRPtrType, IRLabeler.getIdLabel("@.str"));
        root.gStr.add(new IRStrDef(var.name, it.value));
        return new IRhelper(var);
    }

    @Override
    public IRhelper visit(NullExprNode it) {
        return new IRhelper(new IRLiteral("null"));
    }

    @Override
    public IRhelper visit(FmtStringExprNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public IRhelper visit(BlockStmtNode it) {
        for (var stmt : it.stmts) {
            stmt.accept(this);
            if (stmt instanceof ReturnStmtNode || stmt instanceof BreakStmtNode || stmt instanceof ContinueStmtNode) {
                break;
            }
        }
        return null;
    }

    @Override
    public IRhelper visit(VarDefStmtNode it) {
        it.varsDef.accept(this);
        return null;
    }

    @Override
    public IRhelper visit(IfStmtNode it) {
        String iflabel = IRLabeler.getIdLabel("if");
        IRblock if_then = curFunc.newBlock(iflabel + ".then");
        IRblock if_else = curFunc.newBlock(iflabel + ".else");
        IRblock if_end = curFunc.newBlock(iflabel + ".end");

        IRvar cond = (IRvar) it.condition.accept(this).exprVar;
        curBlock.setEndIns(new branchIns(cond, if_then.getLabel(), if_else.getLabel()));

        curBlock = if_then;
        it.thenStmt.accept(this);
        if (!curBlock.isEnd()) curBlock.setEndIns(new jumpIns(if_end.getLabel()));

        curBlock = if_else;
        if (it.elseStmt != null) {
            it.elseStmt.accept(this);
        }
        if (!curBlock.isEnd()) curBlock.setEndIns(new jumpIns(if_end.getLabel()));

        curBlock = if_end;
        return null;
    }

    @Override
    public IRhelper visit(ForStmtNode it) {
        String looplabel = IRLabeler.getIdLabel("for");
        LoopStack_break.add(looplabel + ".end");
        LoopStack_continue.add(looplabel + ".step");

        IRblock for_cond = curFunc.newBlock(looplabel + ".cond");
        IRblock for_body = curFunc.newBlock(looplabel + ".body");
        IRblock for_step = curFunc.newBlock(looplabel + ".step");
        IRblock for_end = curFunc.newBlock(looplabel + ".end");

        if (it.init != null) {
            it.init.accept(this);
        }
        curBlock.setEndIns(new jumpIns(for_cond.getLabel()));

        curBlock = for_cond;
        if (it.cond != null) {
            IRvar cond = (IRvar) it.cond.accept(this).exprVar;
            curBlock.setEndIns(new branchIns(cond, for_body.getLabel(), for_end.getLabel()));
        } else {
            curBlock.setEndIns(new jumpIns(for_body.getLabel()));
        }

        curBlock = for_step;
        if (it.step != null) {
            it.step.accept(this);
        }
        curBlock.setEndIns(new jumpIns(for_cond.getLabel()));

        curBlock = for_body;
        it.body.accept(this);
        curBlock.setEndIns(new jumpIns(for_step.getLabel()));

        curBlock = for_end;
        LoopStack_break.removeLast();
        LoopStack_continue.removeLast();

        return null;
    }

    @Override
    public IRhelper visit(WhileStmtNode it) {
        String looplabel = IRLabeler.getIdLabel("while");
        LoopStack_break.add(looplabel + ".end");
        LoopStack_continue.add(looplabel + ".cond");

        IRblock while_cond = curFunc.newBlock(looplabel + ".cond");
        IRblock while_body = curFunc.newBlock(looplabel + ".body");
        IRblock while_end = curFunc.newBlock(looplabel + ".end");

        curBlock.setEndIns(new jumpIns(while_cond.getLabel()));

        curBlock = while_cond;
        IRitem cond = it.condition.accept(this).exprVar;
        curBlock.setEndIns(new branchIns(cond, while_body.getLabel(), while_end.getLabel()));

        curBlock = while_body;
        it.body.accept(this);
        curBlock.setEndIns(new jumpIns(while_cond.getLabel()));

        curBlock = while_end;
        LoopStack_break.removeLast();
        LoopStack_continue.removeLast();

        return null;
    }

    @Override
    public IRhelper visit(ReturnStmtNode it) {
        if (it.expr != null) {
            IRhelper helper = it.expr.accept(this);
            curBlock.setEndIns(new returnIns(helper.exprVar));
        } else {
            curBlock.setEndIns(new returnIns(new IRLiteral("void")));
        }

        return null;
    }

    @Override
    public IRhelper visit(BreakStmtNode it) {
        curBlock.setEndIns(new jumpIns(LoopStack_break.getLast()));
        return null;
    }

    @Override
    public IRhelper visit(ContinueStmtNode it) {
        curBlock.setEndIns(new jumpIns(LoopStack_continue.getLast()));
        return null;
    }

    @Override
    public IRhelper visit(ExprStmtNode it) {
        it.expr.accept(this);
        return null;
    }

    @Override
    public IRhelper visit(EmptyStmtNode it) {
        return null;
    }

}
