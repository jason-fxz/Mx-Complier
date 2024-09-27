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
import IR.type.*;
import Util.BuiltinElements;
import Util.IRLabeler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class IRBuilder implements ASTVisitor<IRhelper> {
    private IRblock curBlock;
    private IRFuncDef curFunc;
    private IRFuncDef gInit;
    private IRRoot root;
    private String curClassName;
    private ArrayList<IRvar> curClassMembers;
    private ArrayList<String> LoopStack_break;
    private ArrayList<String> LoopStack_continue;
    private HashMap<String, Integer> sizeof;


    public IRBuilder() {
        curBlock = null;
        curFunc = null;
        curClassMembers = null;
        sizeof = new HashMap<>();
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

        root.builtinfuncs
                .add(new IRFuncDec("@__mx_string_concat", new IRType("ptr"), new IRType("ptr"), new IRType("ptr")));
        root.builtinfuncs
                .add(new IRFuncDec("@__mx_string_compare", new IRType("i32"), new IRType("ptr"), new IRType("ptr")));
        root.builtinfuncs.add(new IRFuncDec("@string.length", new IRType("i32"), new IRType("ptr")));
        root.builtinfuncs.add(new IRFuncDec("@string.substring", new IRType("ptr"), new IRType("ptr"),
                new IRType("i32"), new IRType("i32")));
        root.builtinfuncs.add(new IRFuncDec("@string.parseInt", new IRType("i32"), new IRType("ptr")));
        root.builtinfuncs.add(new IRFuncDec("@string.ord", new IRType("i32"), new IRType("ptr"), new IRType("i32")));

        root.builtinfuncs.add(new IRFuncDec("@__mx_allocate", new IRType("ptr"), new IRType("i32")));
        root.builtinfuncs
                .add(new IRFuncDec("@__mx_allocate_array", new IRType("ptr"), new IRType("i32"), new IRType("i32")));
        root.builtinfuncs.add(new IRFuncDec("@__mx_array_size", new IRType("i32"), new IRType("ptr")));
        root.builtinfuncs.add(new IRFuncDec("@__mx_bool_to_string", new IRType("ptr"), new IRType("i1")));

        sizeof.put("int", 4);
        sizeof.put("bool", 4);

        // First Collect all Class Define
        it.Defs.forEach(sd -> {
            if (sd instanceof ClassDefNode) {
                var iit = (ClassDefNode) sd;
                sizeof.put(iit.name, iit.classInfo.memberId.size() * 4);
            }
        });
        
        // Then handle the global variable
        curFunc = gInit;
        curBlock = gInit.newBlock("entry.start");
        it.Defs.forEach(sd -> {
            if (sd instanceof VarsDefNode) {
                ((VarsDefNode) sd).varDefs.forEach(vd -> {
                    handleglobalVar(vd);
                });
            }
        });
        curBlock.setEndIns(new returnIns(new IRLiteral(IRType.IRvoidType, "void")));
        root.funcs.add(gInit);
        gInit.entryBlock.setEndIns(new jumpIns("entry.start"));
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
            if (!info.isFunc) {
                classType.AddMember(new IRType(info));
                curClassMembers.add(new IRvar("%this.m." + name));
            }
        });
        

        root.gStrusts.add(new IRStructDef(classType));

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
            IRvar pvar = new IRvar("%this");
            IRvar paddr = new IRvar("%this.addr");
            curFunc.entryBlock.addIns(new allocaIns(paddr, IRType.IRPtrType));
            curFunc.entryBlock.addIns(new storeIns(pvar, paddr));

            for (int i = 0; i < curClassMembers.size(); ++i) {
                IRvar tmpvar = curClassMembers.get(i);
                funcDef.entryBlock.addIns(new getelementptrIns(tmpvar, new IRvar("%this"), "%class." + curClassName,
                        new IRLiteral("0"), new IRLiteral(String.valueOf(i))));
            }
        }

        it.params.forEach(p -> {
            IRvar pvar = new IRvar(p, false);
            IRvar paddr = new IRvar(pvar.name + ".addr");
            funcDef.addParam(pvar);
            curFunc.entryBlock.addIns(new allocaIns(paddr, pvar.type));
            curFunc.entryBlock.addIns(new storeIns(pvar, paddr));
        });

        curBlock = curFunc.newBlock("entry.start");
        
        it.body.accept(this);

        curFunc.entryBlock.setEndIns(new jumpIns("entry.start"));
        curFunc = null;
        curBlock = null;

        tidyUp(funcDef);
        
        
        return null;
    }
    
    // Tidy Up : clean empty blocks & clean unreachable blocks
    private void tidyUp(IRFuncDef funcDef) {
        HashSet<IRblock> visited = new HashSet<>();
        dfs(funcDef.entryBlock, funcDef, visited);
        
        var iterator = funcDef.blocks.entrySet().iterator();
        while (iterator.hasNext()) {
            var entry = iterator.next();
            if (entry.getValue().empty() || !visited.contains(entry.getValue())) {
                iterator.remove();
            }
        }
    }

    private void dfs(IRblock cur, IRFuncDef func, HashSet<IRblock> visited) {
        if (visited.contains(cur)) {
            return;
        }
        visited.add(cur);
        if (cur.endIns instanceof jumpIns) {
            IRblock next = func.blocks.get(((jumpIns) cur.endIns).label);
            dfs(next, func, visited);
        } else if (cur.endIns instanceof branchIns) {
            IRblock then = func.blocks.get(((branchIns) cur.endIns).trueLabel);
            IRblock els = func.blocks.get(((branchIns) cur.endIns).falseLabel);
            dfs(then, func, visited);
            dfs(els, func, visited);
        }
    }

    private IRvar handleTmpVarAlloc(IRType type, String name) {
        IRvar var = new IRvar(IRLabeler.getIdLabel(name));
        curFunc.entryBlock.addIns(new allocaIns(var, type));
        curFunc.entryBlock.addIns(new storeIns(new IRLiteral(type), var));
        return var;
    }

    @Override
    public IRhelper visit(VarDefNode it) {
        IRvar var = new IRvar(it.type.label);
        curFunc.entryBlock.addIns(new allocaIns(var, new IRType(it.type))); // set the alloca instruction
        if (it.init != null) {
            IRhelper t = it.init.accept(this); // calc the init expr
            curBlock.addIns(new storeIns(t.exprVar, var)); // store the result to the variable
        } else {
            curBlock.addIns(new storeIns(new IRLiteral(new IRType(it.type)), var));
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
            IRvar resptr = handleTmpVarAlloc(IRType.IRBoolType, "%" + opstr + ".ptr");
            res = new IRvar(IRType.IRBoolType, IRLabeler.getIdLabel("%" + opstr));

            IRitem lhsvar = it.lhs.accept(this).exprVar; // visit lhs
            curBlock.addIns(new storeIns(lhsvar, resptr));

            IRblock l_rhs = curFunc.newBlock(IRLabeler.getIdLabel("l" + opstr + ".rhs"));
            IRblock l_end = curFunc.newBlock(IRLabeler.getIdLabel("l" + opstr + ".end"));

            IRblock lastcur = curBlock; // land.lhs done
            curBlock = l_rhs;
            IRitem rhsvar = it.rhs.accept(this).exprVar; // visit rhs
            curBlock.addIns(new storeIns(rhsvar, resptr));
            curBlock.setEndIns(new jumpIns(l_end.getLabel()));

            if (it.op.equals("&&")) {
                lastcur.setEndIns(new branchIns(lhsvar, l_rhs.getLabel(), l_end.getLabel()));
                l_end.addIns(new loadIns(res, resptr));
            } else {
                lastcur.setEndIns(new branchIns(lhsvar, l_end.getLabel(), l_rhs.getLabel()));
                l_end.addIns(new loadIns(res, resptr));
            }
            curBlock = l_end;

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
                } else
                    throw new UnsupportedOperationException("WTF? String binary: " + it.op);
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
                curBlock.addIns(new arithIns(res, "sub", new IRLiteral(IRType.IRIntType, "0"), rhs.exprVar));
            } else if (it.op.in("~")) {
                res = new IRvar(IRType.IRIntType, IRLabeler.getIdLabel("%not"));
                curBlock.addIns(new arithIns(res, "xor", rhs.exprVar, new IRLiteral("-1")));
            } else
                throw new UnsupportedOperationException("WTF? int LeftSingleExprNode");
        } else if (rhsinfo.equals(BuiltinElements.boolType)) {
            if (it.op.in("!")) {
                res = new IRvar(IRType.IRBoolType, IRLabeler.getIdLabel("%not"));
                curBlock.addIns(new arithIns(res, "xor", rhs.exprVar, new IRLiteral("true")));
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

        if (lhsinfo.equals(BuiltinElements.intType)) {
            if (it.op.in("++")) {
                IRvar tmp = new IRvar(IRType.IRIntType, IRLabeler.getIdLabel("%Rselfinc"));
                curBlock.addIns(new arithIns(tmp, "add", lhs.exprVar, new IRLiteral("1")));
                curBlock.addIns(new storeIns(tmp, lhs.exprAddr));
                helper.exprVar = lhs.exprVar;
            } else if (it.op.in("--")) {
                IRvar tmp = new IRvar(IRType.IRIntType, IRLabeler.getIdLabel("%Rselfdec"));
                curBlock.addIns(new arithIns(tmp, "sub", lhs.exprVar, new IRLiteral("1")));
                curBlock.addIns(new storeIns(tmp, lhs.exprAddr));
                helper.exprVar = lhs.exprVar;
            } else
                throw new UnsupportedOperationException("WTF? int RightSingleExprNode");
        } else
            throw new UnsupportedOperationException("WTF?? RightSingleExprNode");
        return helper;
    }

    @Override
    public IRhelper visit(ConditionExprNode it) {
        IRitem cond = it.cond.accept(this).exprVar;
        IRblock cond_ture = curFunc.newBlock(IRLabeler.getIdLabel("cond.true"));
        IRblock cond_false = curFunc.newBlock(IRLabeler.getIdLabel("cond.false"));
        IRblock cond_end = curFunc.newBlock(IRLabeler.getIdLabel("cond.end"));

        curBlock.setEndIns(new branchIns(cond, cond_ture.getLabel(), cond_false.getLabel()));

        IRvar resptr = null;
        if (!it.thenExpr.info.isVoid) {
            resptr = handleTmpVarAlloc(new IRType(it.thenExpr.info), "%cond.ptr");
        }

        curBlock = cond_ture;
        IRitem truevar = it.thenExpr.accept(this).exprVar;
        if (resptr != null) {
            curBlock.addIns(new storeIns(truevar, resptr));
        }
        curBlock.setEndIns(new jumpIns(cond_end.getLabel()));

        curBlock = cond_false;
        IRitem falsevar = it.elseExpr.accept(this).exprVar;
        if (resptr != null) {
            curBlock.addIns(new storeIns(falsevar, resptr));
        }
        curBlock.setEndIns(new jumpIns(cond_end.getLabel()));

        curBlock = cond_end;
        if (resptr != null) {
            IRvar res = new IRvar(truevar.type, IRLabeler.getIdLabel("%cond"));
            curBlock.addIns(new loadIns(res, resptr));

            return new IRhelper(res);
        } else {
            return new IRhelper();
        }
    
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
                IRvar var = new IRvar(IRType.IRPtrType, IRLabeler.getIdLabel("%this"));
                curBlock.addIns(new loadIns(var, new IRvar("%this.addr")));
                helper.funthis = var;
            }
            return helper;
        } else if (it.info.label.equals("%this")) {
            IRvar var = new IRvar(IRType.IRPtrType, IRLabeler.getIdLabel("%this"));
            curBlock.addIns(new loadIns(var, new IRvar("%this.addr")));
            return new IRhelper(var);
        } else {
            // if (it.info.label.startsWith("!this!")) {
            // IRvar tmpthis = new IRvar(IRType.IRPtrType,
            // IRLabeler.getIdLabel("%atom.this"));
            // curBlock.addIns(new loadIns(tmpthis, new IRvar("%this.addr")));
            // IRvar varaddr = new IRvar(IRLabeler.getIdLabel("%member." + it.name +
            // ".addr"));
            // IRvar tmpvar = new IRvar(new IRType(it.info), IRLabeler.getIdLabel("%member."
            // + it.name));

            // int elementId = Integer.parseInt(it.info.label.substring(6));
            // curBlock.addIns(new getelementptr(varaddr, tmpthis, "%class." + curClassName,
            // 0, elementId));
            // curBlock.addIns(new loadIns(tmpvar, varaddr));

            // return new IRhelper(tmpvar, varaddr);
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
            helper.funthis = (IRvar) obj.exprVar;
        } else {
            IRvar tmpvar = new IRvar(new IRType(it.info), IRLabeler.getIdLabel("%" + it.member));
            IRvar varaddr = new IRvar(IRLabeler.getIdLabel("%" + it.member + ".addr"));

            int elementId = Integer.parseInt(it.info.label);
            curBlock.addIns(new getelementptrIns(varaddr, obj.exprVar, "%class." + it.object.info.typeName,
                    new IRLiteral("0"), new IRLiteral(String.valueOf(elementId))));
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
            IRvar res = new IRvar(new IRType(it.info), IRLabeler.getIdLabel("%call"));
            curBlock.addIns(new callIns(res, func.funName, args));
            helper.exprVar = res;
        }

        return helper;
    }

    @Override
    public IRhelper visit(NewVarExprNode it) {
        var info = it.info;
        IRvar var = new IRvar(IRLabeler.getIdLabel("%new." + it.info.typeName));
        curBlock.addIns(new callIns(var, "__mx_allocate", new IRLiteral(sizeof.get(info.typeName).toString())));
        curBlock.addIns(new callIns(it.info.typeName + "." + it.info.typeName, var));
        return new IRhelper(var);
    }

    private IRitem handleNewArray(ArrayList<IRitem> asize, int idx) {
        if (idx == asize.size())
            return new IRLiteral("null");
        IRvar var = new IRvar(IRLabeler.getIdLabel("%new.array"));
        // call __mx_allocate_array
        curBlock.addIns(new callIns(var, "__mx_allocate_array", new IRLiteral("4"), asize.get(idx)));

        String looplabel = IRLabeler.getIdLabel("arrayinit.for");
        LoopStack_break.add(looplabel + ".end");
        LoopStack_continue.add(looplabel + ".step");

        IRblock for_cond = curFunc.newBlock(looplabel + ".cond");
        IRblock for_body = curFunc.newBlock(looplabel + ".body");
        IRblock for_step = curFunc.newBlock(looplabel + ".step");
        IRblock for_end = curFunc.newBlock(looplabel + ".end");

        // init: i = 0
        IRvar variaddr = handleTmpVarAlloc(IRType.IRIntType, "%tmp");
        curBlock.addIns(new storeIns(new IRLiteral("0"), variaddr));
        curBlock.setEndIns(new jumpIns(for_cond.getLabel()));

        curBlock = for_cond;
        IRvar cond = new IRvar(IRType.IRBoolType, IRLabeler.getIdLabel("%tmp"));
        IRvar vari = new IRvar(IRType.IRIntType, IRLabeler.getIdLabel("%tmp"));

        curBlock.addIns(new loadIns(vari, variaddr));
        curBlock.addIns(new icmpIns(cond, "slt", vari, asize.get(idx)));
        curBlock.setEndIns(new branchIns(cond, for_body.getLabel(), for_end.getLabel()));

        curBlock = for_step;
        IRvar vari1 = new IRvar(IRType.IRIntType, IRLabeler.getIdLabel("%tmp"));
        IRvar vari2 = new IRvar(IRType.IRIntType, IRLabeler.getIdLabel("%tmp"));
        curBlock.addIns(new loadIns(vari1, variaddr));
        curBlock.addIns(new arithIns(vari2, "add", vari1, new IRLiteral("1")));
        curBlock.addIns(new storeIns(vari2, variaddr));
        curBlock.setEndIns(new jumpIns(for_cond.getLabel()));

        curBlock = for_body;
        IRvar sub_array_addr = new IRvar(IRType.IRPtrType, IRLabeler.getIdLabel("%array.addr"));
        IRitem sub_array_var = handleNewArray(asize, idx + 1);
        IRvar vari3 = new IRvar(IRType.IRIntType, IRLabeler.getIdLabel("%tmp"));

        curBlock.addIns(new loadIns(vari3, variaddr));
        curBlock.addIns(new getelementptrIns(sub_array_addr, var, "ptr", vari3));
        curBlock.addIns(new storeIns(sub_array_var, sub_array_addr));
        curBlock.setEndIns(new jumpIns(for_step.getLabel()));

        curBlock = for_end;
        LoopStack_break.removeLast();
        LoopStack_continue.removeLast();

        return var;
    }

    @Override
    public IRhelper visit(NewArrayExprNode it) {
        if (it.array != null) {
            return it.array.accept(this);
        } else {
            ArrayList<IRitem> asize = new ArrayList<>();
            for (var expr : it.dimsize) {
                if (expr == null) {
                    break;
                }
                IRitem sz = expr.accept(this).exprVar;
                asize.add(sz);
            }

            return new IRhelper(handleNewArray(asize, 0));
        }

    }

    @Override
    public IRhelper visit(ArrayInitNode it) {
        IRvar var = new IRvar(IRLabeler.getIdLabel("%array.init"));
        curBlock.addIns(new callIns(var, "__mx_allocate_array", new IRLiteral("4"),
                new IRLiteral(String.valueOf(it.exprs.size()))));
        // if (it.dep == 1) {
        for (int i = 0; i < it.exprs.size(); ++i) {
            IRitem expritem = it.exprs.get(i).accept(this).exprVar;
            IRvar addr = new IRvar(IRLabeler.getIdLabel("%array.init.tmpaddr"));
            curBlock.addIns(new getelementptrIns(addr, var, "ptr", new IRLiteral(String.valueOf(i))));
            curBlock.addIns(new storeIns(expritem, addr));
        }
        // } else {
        // for (int i = 0; i < it.exprs.size(); ++i) {
        // IRitem expritem = it.exprs.get(i).accept(this).exprVar;
        // IRvar addr = new IRvar(IRLabeler.getIdLabel("%array.init.tmpaddr"));
        // curBlock.addIns(new getelementptr(addr, var, "ptr", new
        // IRLiteral(String.valueOf(i))));
        // curBlock.addIns(new storeIns(expritem, addr));
        // }
        // }
        return new IRhelper(var);
    }

    @Override
    public IRhelper visit(ArrayExprNode it) {
        IRvar array = (IRvar) it.array.accept(this).exprVar;
        IRitem index = it.index.accept(this).exprVar;
        IRvar var = null;
        if (it.info.isArray()) {
            var = new IRvar(IRLabeler.getIdLabel("%array.get"));
        } else {
            var = new IRvar(new IRType(it.info), IRLabeler.getIdLabel("%array.get"));
        }
        IRvar addr = new IRvar(IRLabeler.getIdLabel("%array.get.addr"));

        curBlock.addIns(new getelementptrIns(addr, array, "ptr", index));
        curBlock.addIns(new loadIns(var, addr));

        return new IRhelper(var, addr);
    }

    @Override
    public IRhelper visit(IntExprNode it) {
        return new IRhelper(new IRLiteral(IRType.IRIntType, String.valueOf(it.value)));
    }

    @Override
    public IRhelper visit(BoolExprNode it) {
        return new IRhelper(new IRLiteral(IRType.IRBoolType, String.valueOf(it.value)));
    }

    private IRvar handleStrDef(String str) {
        IRvar var = new IRvar(IRType.IRPtrType, IRLabeler.getIdLabel("@.str"));
        root.gStrs.add(new IRStrDef(var.name, str));
        return var;
    }

    private IRvar handleStrCat(IRvar lhs, IRvar rhs) {
        IRvar res = new IRvar(IRType.IRPtrType, IRLabeler.getIdLabel("%str.concat"));
        curBlock.addIns(new callIns(res, "__mx_string_concat", lhs, rhs));
        return res;
    }

    private IRvar handletoStr(IRitem var) {
        IRvar res = new IRvar(IRType.IRPtrType, IRLabeler.getIdLabel("%.toStr"));
        if (var.type.equals(IRType.IRBoolType)) {
            curBlock.addIns(new callIns(res, "__mx_bool_to_string", var));
        } else if (var.type.equals(IRType.IRIntType)) {
            curBlock.addIns(new callIns(res, "toString", var));
        } else {
            return (IRvar)var;
        }
            // throw new UnsupportedOperationException("WTF? toStr: " + var.type);
        return res;
    }

    @Override
    public IRhelper visit(StringExprNode it) {
        IRvar var = handleStrDef(it.value);
        return new IRhelper(var);
    }

    @Override
    public IRhelper visit(NullExprNode it) {
        return new IRhelper(new IRLiteral("null"));
    }

    @Override
    public IRhelper visit(FmtStringExprNode it) {
        IRvar fmt = handleStrDef(it.strlist.get(0));
        for (int i = 0; i < it.exprlist.size(); ++i) {
            IRitem expr = it.exprlist.get(i).accept(this).exprVar;
            fmt = handleStrCat(fmt, handletoStr(expr));
            IRvar tmpstr = handleStrDef(it.strlist.get(i + 1));
            fmt = handleStrCat(fmt, tmpstr);
        }
        return new IRhelper(fmt);
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

        IRitem cond = it.condition.accept(this).exprVar;
        curBlock.setEndIns(new branchIns(cond, if_then.getLabel(), if_else.getLabel()));

        curBlock = if_then;
        it.thenStmt.accept(this);
        if (!curBlock.isEnd())
            curBlock.setEndIns(new jumpIns(if_end.getLabel()));

        curBlock = if_else;
        if (it.elseStmt != null) {
            it.elseStmt.accept(this);
        }
        if (!curBlock.isEnd())
            curBlock.setEndIns(new jumpIns(if_end.getLabel()));

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
            IRitem cond = it.cond.accept(this).exprVar;
            curBlock.setEndIns(new branchIns(cond, for_body.getLabel(), for_end.getLabel()));
        } else {
            curBlock.setEndIns(new jumpIns(for_body.getLabel()));
        }

        curBlock = for_step;
        if (it.step != null) {
            it.step.accept(this);
        }
        if (!curBlock.isEnd()) curBlock.setEndIns(new jumpIns(for_cond.getLabel()));

        curBlock = for_body;
        it.body.accept(this);
        if (!curBlock.isEnd()) curBlock.setEndIns(new jumpIns(for_step.getLabel()));

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
        if (!curBlock.isEnd()) curBlock.setEndIns(new jumpIns(while_cond.getLabel()));

        curBlock = while_end;
        LoopStack_break.removeLast();
        LoopStack_continue.removeLast();

        return null;
    }

    @Override
    public IRhelper visit(ReturnStmtNode it) {
        if (it.expr != null) {
            IRhelper helper = it.expr.accept(this);
            if (!curBlock.isEnd()) curBlock.setEndIns(new returnIns(helper.exprVar));
        } else {
            if (!curBlock.isEnd()) curBlock.setEndIns(new returnIns(new IRLiteral("void")));
        }

        return null;
    }

    @Override
    public IRhelper visit(BreakStmtNode it) {
        if (!curBlock.isEnd()) curBlock.setEndIns(new jumpIns(LoopStack_break.getLast()));
        return null;
    }

    @Override
    public IRhelper visit(ContinueStmtNode it) {
        if (!curBlock.isEnd()) curBlock.setEndIns(new jumpIns(LoopStack_continue.getLast()));
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
