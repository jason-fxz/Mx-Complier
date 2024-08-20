package Frontend;

import AST.*;
import AST.Node.*;
import AST.Node.def.*;
import AST.Node.expr.*;
import AST.Node.stmt.*;
import Util.scope.*;
import Util.BuiltinElements;
import Util.position;
import Util.error.*;
import Util.info.ClassInfo;
import Util.info.ExprInfo;
import Util.info.TypeInfo;

public class SemanticChecker implements ASTVisitor<Void> {

    private Scope curScope;
    private globalScope gScope;
    private boolean haveMain = false;

    private void enterScope(Scope scope) {
        curScope = scope;
    }

    private void exitScope() {
        curScope = curScope.parScope();
    }

    private boolean checkTypeValid(TypeInfo type) {
        return (type.isBasic && !type.isVoid) || (gScope.haveClass(type.typeName));
    }

    public SemanticChecker(globalScope gScope) {
        this.curScope = this.gScope = gScope;
        this.haveMain = false;
    }

    @Override
    public Void visit(RootNode it) {
        it.Defs.forEach(sd -> sd.accept(this));
        if (!haveMain) {
            throw new SemanticError("No main function", it.pos);
        }
        return null;
    }

    @Override
    public Void visit(ClassDefNode it) {
        var classScope = new classScope(curScope);
        enterScope(classScope);
        classScope.className = it.name;
        it.classInfo = classScope.classInfo = gScope.GetClassInfo(it.name);
        var classInfo = gScope.GetClassInfo(it.name);
        classInfo.GetMembers().forEach((name, type) -> {
            classScope.DefVar(name, type, it.pos);
        });
        classInfo.GetMethods().forEach((name, func) -> {
            classScope.DefFunc(name, func);
        });
        if (it.constructor != null) {
            it.constructor.accept(this);
        } else {
            it.constructor = new FuncDefNode(new position(), new BlockStmtNode(new position()));
            it.constructor.name = it.name;
            it.constructor.type = BuiltinElements.voidType;
            it.constructor.body.stmts.add(new ReturnStmtNode(new position(), null));
        }
        for (var funcDef : it.funcDefs) {
            funcDef.accept(this);
        }
        exitScope();
        return null;
    }

    @Override
    public Void visit(FuncDefNode it) {
        var funcScope = new funcScope(curScope);
        enterScope(funcScope);
        funcScope.retType = new TypeInfo(it.type);
        // put parameters into scope
        it.params.forEach(par -> {
            funcScope.DefParamVar(par.name, par.type, par.pos);
        });
        for (var stmt : it.body.stmts) {
            stmt.accept(this);
        }
        // check main function
        if (it.name.equals("main")) {
            if (!it.type.equals(BuiltinElements.intType)) {
                throw new SemanticError("Main function should return int but got " + it.type.GetTypeName(), it.pos);
            }
            if (it.params.size() != 0) {
                throw new SemanticError("Main function should have no parameters", it.pos);
            }
            haveMain = true;
        }

        if (!funcScope.haveRet && !it.type.equals(BuiltinElements.voidType) && !it.name.equals("main")) {
            throw new MissingReturnStatementError("Function " + it.name + " should have return statement", it.pos);
        }
        if (!funcScope.haveRet) {
            if (it.name.equals("main")) {
                it.body.stmts.add(new ReturnStmtNode(new position(), new IntExprNode(0, new position())));
            } else {
                it.body.stmts.add(new ReturnStmtNode(new position(), null));
            }
        }
        if (it.type.equals(BuiltinElements.voidType)) {
            it.body.stmts.add(new ReturnStmtNode(new position(), null));
        }
        exitScope();
        return null;
    }

    @Override
    public Void visit(VarDefNode it) {
        if (!checkTypeValid(it.type)) {
            throw new InvalidTypeError("Invalid type " + it.type.GetTypeName(), it.pos);
        }
        if (curScope.haveVar(it.name, false)) {
            throw new MultipleDefinitionsError("Variable " + it.name + " already defined", it.pos);
        }
        if (it.init != null) {
            if (it.type.isArray() && it.init instanceof ArrayInitNode) {
                var arrayInit = (ArrayInitNode) it.init;
                arrayInit.dep = it.type.dim;
                arrayInit.info = new ExprInfo(it.type.typeName, it.type.dim, true);
            }

            it.init.accept(this);
            if (!it.init.info.equals(it.type)) {
                throw new TypeMismatchError("Cannot assign " + it.init.info.GetTypeName() + " to " + it.type.GetTypeName(),
                        it.pos);
            }
        }
        curScope.DefVar(it.name, it.type, it.pos);
        it.type.label = curScope.getVarLabel(it.name); // set label 
        return null;
    }

    @Override
    public Void visit(VarsDefNode it) {
        for (var varDef : it.varDefs) {
            varDef.accept(this);
        }
        return null;
    }

    @Override
    public Void visit(BinaryExprNode it) {
        it.lhs.accept(this);
        it.rhs.accept(this);
        var lhsinfo = it.lhs.info;
        var rhsinfo = it.rhs.info;
        if (lhsinfo.isFunc) {
            throw new InvalidTypeError("Cannot perform binary operation on function " + lhsinfo.GetTypeName(), it.pos);
        }
        if (rhsinfo.isFunc) {
            throw new InvalidTypeError("Cannot perform binary operation on function " + rhsinfo.GetTypeName(), it.pos);
        }
        if (!lhsinfo.equals(rhsinfo)) {
            throw new TypeMismatchError("Cannot perform binary operation on different types " + lhsinfo.GetTypeName()
                    + " and " + rhsinfo.GetTypeName(), it.pos);
        }
        if (lhsinfo.dim == 0) { // non-array
            if (lhsinfo.equals(BuiltinElements.intType)) { // int type
                if (it.op.in("&&", "||")) { // logic op
                    throw new InvalidTypeError("Operator " + it.op + " is not supported for int", it.pos);
                } else if (it.op.in("==", "!=", "<", ">", "<=", ">=")) { // camp op
                    it.info = new ExprInfo("bool", false);
                } else { // arith op / bit op
                    it.info = new ExprInfo("int", false);
                }
            } else if (lhsinfo.equals(BuiltinElements.boolType)) {
                if (it.op.in("==", "!=", "&&", "||")) {
                    it.info = new ExprInfo("bool", false);
                } else {
                    throw new InvalidTypeError("Operator " + it.op + " is not supported for bool", it.pos);
                }
            } else if (lhsinfo.equals(BuiltinElements.stringType)) {
                if (it.op.equals("+")) { // str concat
                    it.info = new ExprInfo("string", false);
                } else if (it.op.in("==", "!=", "<", ">", "<=", ">=")) {
                    it.info = new ExprInfo("bool", false);
                } else {
                    throw new InvalidTypeError("Operator " + it.op + " is not supported for string", it.pos);
                }
            } else { // class
                if (it.op.in("==", "!=")) {
                    it.info = new ExprInfo("bool", false);
                } else {
                    throw new InvalidTypeError("Operator " + it.op + " is not supported for class", it.pos);
                }
            }
        } else { // array
            if (it.op.in("==", "!=")) {
                it.info = new ExprInfo("bool", false);
            } else {
                throw new InvalidTypeError("Operator " + it.op + " is not supported for array", it.pos);
            }
        }
        return null;
    }

    @Override
    public Void visit(LeftSingleExprNode it) {
        it.rhs.accept(this);
        var rhsinfo = it.rhs.info;
        if (rhsinfo.isFunc) {
            throw new TypeMismatchError("Cannot perform unary operation on function " + rhsinfo.GetTypeName(), it.pos);
        }
        if (rhsinfo.equals(BuiltinElements.intType)) {
            if (it.op.in("++", "--")) {
                if (!rhsinfo.isLvalue) {
                    throw new TypeMismatchError("Cannot perform unary operation on rvalue", it.pos);
                }
                it.info = new ExprInfo("int", true);
            } else if (it.op.in("-", "~")) {
                it.info = new ExprInfo("int", false);
            } else {
                throw new TypeMismatchError("Left unary operator " + it.op + " is not supported for int", it.pos);
            }
        } else if (rhsinfo.equals(BuiltinElements.boolType)) {
            if (it.op.in("!")) {
                it.info = new ExprInfo("bool", false);
            } else {
                throw new TypeMismatchError("Left unary operator " + it.op + " is not supported for bool", it.pos);
            }
        } else {
            throw new TypeMismatchError("Cannot perform unary operation on type " + rhsinfo.GetTypeName(), it.pos);
        }
        return null;
    }

    @Override
    public Void visit(RightSingleExprNode it) {
        it.lhs.accept(this);
        var lhsinfo = it.lhs.info;
        if (lhsinfo.isFunc) {
            throw new TypeMismatchError("Cannot perform unary operation on function " + lhsinfo.GetTypeName(), it.pos);
        }
        if (lhsinfo.equals(BuiltinElements.intType)) {
            if (it.op.in("++", "--")) {
                if (!lhsinfo.isLvalue) {
                    throw new TypeMismatchError("Cannot perform unary operation on rvalue", it.pos);
                }
                it.info = new ExprInfo("int", false);
            } else {
                throw new TypeMismatchError("Right unary operator " + it.op + " is not supported for int", it.pos);
            }
        } else {
            throw new TypeMismatchError("Cannot perform unary operation on type " + lhsinfo.GetTypeName(), it.pos);
        }
        return null;
    }

    @Override
    public Void visit(ConditionExprNode it) {
        it.cond.accept(this);
        it.thenExpr.accept(this);
        it.elseExpr.accept(this);
        if (!it.cond.info.equals(BuiltinElements.boolType)) {
            throw new InvalidTypeError("Condition expression should be bool " + it.cond.info.GetTypeName(), it.pos);
        }
        if (!it.thenExpr.info.equals(it.elseExpr.info)) {
            throw new TypeMismatchError("Different types in ternary expression " + it.thenExpr.info.GetTypeName() + " and "
                    + it.elseExpr.info.GetTypeName(), it.pos);
        }
        it.info = new ExprInfo(it.thenExpr.info.isNull() ? it.elseExpr.info : it.thenExpr.info);
        return null;
    }

    @Override
    public Void visit(AssignExprNode it) {
        it.lhs.accept(this);
        var lhsinfo = it.lhs.info;
        if (!lhsinfo.isLvalue) {
            throw new TypeMismatchError("Assign to rvalue " + it.lhs.toString(), it.pos);
        }

        // if (lhsinfo.isArray() && it.rhs instanceof ArrayInitNode) {
        //     var arrayInit = (ArrayInitNode) it.rhs;
        //     arrayInit.dep = lhsinfo.dim;
        //     arrayInit.info = new ExprInfo(lhsinfo.typeName, lhsinfo.dim, true);
        // }

        it.rhs.accept(this);
        var rhsinfo = it.rhs.info;
        if (lhsinfo.equals(rhsinfo)) {
            it.info = new ExprInfo(lhsinfo);
        } else {
            throw new TypeMismatchError("Cannot assign " + rhsinfo.GetTypeName() + " to " + lhsinfo.GetTypeName(), it.pos);
        }
        return null;
    }

    @Override
    public Void visit(AtomExprNode it) {
        if (it.name.equals("this")) {
            var lastclass = (classScope)curScope.getLastClass();
            if (lastclass == null) {
                throw new UndefinedIdentifierError("this should be in class", it.pos);
            }
            it.info = new ExprInfo(lastclass.className, false);
            it.info.label = "%this";
        } else {
            var atomType = curScope.getVarType(it.name, true);
            if (atomType == null) {
                throw new UndefinedIdentifierError("Identifier " + it.name + " not defined", it.pos);
            }
            it.info = new ExprInfo(atomType);
            if (atomType.isFunc) {
                it.info.funcinfo = curScope.GetFuncInfo(it.name);
                it.info.isFunc = true;
                it.info.label = it.info.funcinfo.label;
            } else {
                it.info.isLvalue = true;
                it.info.label = curScope.getVarLabel(it.name);
            }
        }
        return null;
    }

    @Override
    public Void visit(MemberExprNode it) {
        it.object.accept(this);
        var objectType = it.object.info;
        if (objectType.equals(BuiltinElements.intType) || objectType.equals(BuiltinElements.boolType)) {
            throw new InvalidTypeError("Cannot access member of non-class type " + objectType.GetTypeName(), it.pos);
        }
        if (objectType.isNull()) {
            throw new InvalidTypeError("Cannot access member of null type", it.pos);
        }
        if (objectType.isFunc) {
            throw new InvalidTypeError("Cannot access member of function type " + objectType.GetTypeName(), it.pos);
        }

        if (objectType.dim > 0) { // array
            if (it.member.equals("size")) {
                it.info = new ExprInfo(BuiltinElements.arraySizeFunc);
                it.info.funcinfo = BuiltinElements.arraySizeFunc;
                it.info.label = "__mx_array_size";
            } else {
                throw new UndefinedIdentifierError(
                        "Call to undefined member " + it.member + " of array type " + objectType.GetTypeName(), it.pos);
            }
        } else { // class
            ClassInfo classInfo = null;
            if (objectType.equals(BuiltinElements.thisType)) {
                var classScope = (classScope) curScope.getLastClass();
                if (classScope == null)
                    throw new UndefinedIdentifierError("this should be used in class", it.pos);
                classInfo = gScope.GetClassInfo(classScope.className);
            } else {
                classInfo = gScope.GetClassInfo(objectType.typeName);
            }

            if (classInfo == null) {
                throw new UndefinedIdentifierError("Call to undefined class " + objectType.GetTypeName(), it.pos);
            }
            var memberInfo = classInfo.GetMemberType(it.member);
            if (memberInfo == null) {
                throw new UndefinedIdentifierError(
                        "Call to undefined member " + it.member + " of class " + objectType.GetTypeName(), it.pos);
            }
            it.info = new ExprInfo(memberInfo);
            if (memberInfo.isFunc) {
                it.info.funcinfo = classInfo.GetMethod(it.member);
                it.info.isFunc = true;
                it.info.label = it.info.funcinfo.label;
            } else {
                it.info.isLvalue = true;
                // class member label
                it.info.label = String.valueOf(classInfo.GetMemberId(it.member));
            }
        }
        return null;
    }

    @Override
    public Void visit(FuncExprNode it) {
        it.func.accept(this);
        it.args.forEach(arg -> arg.accept(this));
        var funcExprInfo = it.func.info;
        if (!funcExprInfo.isFunc) {
            throw new InvalidTypeError("Call to non-function type " + funcExprInfo.GetTypeName(), it.pos);
        }
        var funcDefInfo = funcExprInfo.funcinfo;
        if (funcDefInfo == null) {
            throw new RuntimeException("FUCK");
        }
        if (it.args.size() != funcDefInfo.argsType.size()) {
            throw new TypeMismatchError("Function " + funcDefInfo.label + " expects " + funcDefInfo.argsType.size()
                    + " arguments, but got " + it.args.size(), it.pos);
        }

        for (int i = 0; i < it.args.size(); i++) {
            var argType = it.args.get(i).info;
            var paramType = funcDefInfo.argsType.get(i);
            if (!argType.equals(paramType)) {
                throw new TypeMismatchError("Function " + funcDefInfo.label + " expects " + paramType.GetTypeName()
                        + " but got " + argType.GetTypeName(), it.pos);
            }
        }

        it.info = new ExprInfo(funcDefInfo.retType);
        return null;
    }

    @Override
    public Void visit(NewVarExprNode it) {
        it.info = new ExprInfo(it.name, false);
        if (!checkTypeValid(it.info) || (it.info.isBasic)) {
            throw new TypeMismatchError("Connot initialize type " + it.info.GetTypeName(), it.pos);
        }
        return null;
    }

    @Override
    public Void visit(NewArrayExprNode it) {
        boolean haveinit = false;
        if (it.array != null) {
            haveinit = true;
            it.array.dep = it.dim; // up to down set info
            it.array.info = new ExprInfo(it.name, it.dim, true);
            it.array.accept(this);
        }
        it.info = new ExprInfo(it.name, it.dim, true);

        for (var expr : it.dimsize) {
            if (expr == null)
                break;
            if (haveinit) {
                throw new SemanticError("Array with initializer should not define size", it.pos);
            }
            expr.accept(this);
            if (!expr.info.equals(BuiltinElements.intType)) {
                throw new InvalidTypeError("Array size should be int", it.pos);
            }
        }
        return null;
    }

    @Override
    public Void visit(ArrayInitNode it) {
        if (it.dep == 1) {
            for (var expr : it.exprs) {
                expr.accept(this);
                if (!(expr instanceof LiteralExprNode)) {
                    throw new InvalidTypeError("Array initializer should be literal", it.pos);
                }
                if (!expr.info.typeName.equals(it.info.typeName) && !expr.info.isNull()) {
                    throw new TypeMismatchError("Array initializer type mismatch", it.pos);
                }
            }
        } else {
            for (var expr : it.exprs) {
                if (expr instanceof NullExprNode) {
                    continue;
                } else if (expr instanceof ArrayInitNode) {
                    ArrayInitNode subarray = (ArrayInitNode) expr;
                    subarray.dep = it.dep - 1;
                    subarray.info = new ExprInfo(it.info.typeName, subarray.dep, true);
                    expr.accept(this);
                } else {
                    throw new TypeMismatchError("Array initializer type mismatch", it.pos);
                }
            }
        }
        return null;
    }

    @Override
    public Void visit(ArrayExprNode it) {
        it.array.accept(this);
        it.index.accept(this);
        var arrayType = it.array.info;
        if (!arrayType.isArray()) {
            throw new DimensionOutOfBoundError("Access to non-array type " + arrayType.GetTypeName(), it.array.pos);
        }
        if (!it.index.info.equals(BuiltinElements.intType)) {
            throw new InvalidTypeError("Array index should be int", it.index.pos);
        }
        it.info = new ExprInfo(arrayType.typeName, arrayType.dim - 1, true);
        return null;
    }

    @Override
    public Void visit(IntExprNode it) {
        it.info = new ExprInfo(BuiltinElements.intType);
        return null;
    }

    @Override
    public Void visit(BoolExprNode it) {
        it.info = new ExprInfo(BuiltinElements.boolType);
        return null;
    }

    @Override
    public Void visit(StringExprNode it) {
        it.info = new ExprInfo(BuiltinElements.stringType);
        return null;
    }

    @Override
    public Void visit(NullExprNode it) {
        it.info = new ExprInfo(BuiltinElements.nullType);
        return null;
    }

    @Override
    public Void visit(FmtStringExprNode it) {
        it.exprlist.forEach(expr -> expr.accept(this));
        it.info = new ExprInfo(BuiltinElements.stringType);
        it.exprlist.forEach(expr -> {
            if (!expr.info.isBasic || expr.info.isVoid) {
                throw new InvalidTypeError("Format string can't contain type " + expr.info.GetTypeName(), it.pos);
            }
        });
        return null;
    }

    @Override
    public Void visit(BlockStmtNode it) {
        Scope blockScope = new Scope(curScope);
        enterScope(blockScope);
        it.stmts.forEach(stmt -> stmt.accept(this));
        exitScope();
        return null;
    }

    @Override
    public Void visit(VarDefStmtNode it) {
        it.varsDef.accept(this);
        return null;
    }

    @Override
    public Void visit(IfStmtNode it) {
        it.condition.accept(this);
        if (!it.condition.info.equals(BuiltinElements.boolType)) {
            throw new InvalidTypeError("Condition expression should be bool but got" + it.condition.info.GetTypeName(),
                    it.condition.pos);
        }
        // then
        Scope thenScope = new Scope(curScope);
        enterScope(thenScope);
        it.thenStmt.accept(this);
        exitScope();
        // else
        if (it.elseStmt != null) {
            Scope elseScope = new Scope(curScope);
            enterScope(elseScope);
            it.elseStmt.accept(this);
            exitScope();
        }
        return null;
    }

    @Override
    public Void visit(ForStmtNode it) {
        Scope forScope = new Scope(curScope, Scope.ScopeType.loopScope);
        enterScope(forScope);
        if (it.init != null) {
            it.init.accept(this);
        }
        if (it.cond != null) {
            it.cond.accept(this);
            if (!it.cond.info.equals(BuiltinElements.boolType)) {
                throw new InvalidTypeError("Condition expression should be bool but got" + it.cond.info.GetTypeName(),
                        it.cond.pos);
            }
        }
        if (it.step != null) {
            it.step.accept(this);
        }
        it.body.accept(this);
        exitScope();
        return null;
    }

    @Override
    public Void visit(WhileStmtNode it) {
        Scope whileScope = new Scope(curScope, Scope.ScopeType.loopScope);
        enterScope(whileScope);
        if (it.condition != null) {
            it.condition.accept(this);
            if (!it.condition.info.equals(BuiltinElements.boolType)) {
                throw new InvalidTypeError(
                        "While condition expression should be bool but got" + it.condition.info.GetTypeName(),
                        it.condition.pos);
            }
        } else
            throw new InvalidTypeError("While condition is empty", it.condition.pos);
        it.body.accept(this);
        exitScope();
        return null;
    }

    @Override
    public Void visit(ReturnStmtNode it) {
        if (it.expr != null) {
            it.expr.accept(this);
        }

        var funcScope = (funcScope) curScope.getLastFunc();
        if (funcScope == null) {
            throw new InvalidControlFlowError("Return statement should be in function", it.pos);
        }
        if (it.expr == null) {
            if (!funcScope.retType.equals(BuiltinElements.voidType)) {
                throw new TypeMismatchError("Return type mismatch", it.pos);
            }
        } else {
            if (it.expr.info.equals(BuiltinElements.thisType)) {
                var curClass = (classScope)curScope.getLastClass();
                if (!curClass.className.equals(funcScope.retType.typeName)) {
                    throw new TypeMismatchError("Return type mismatch", it.pos);
                }
            } else if (!it.expr.info.equals(funcScope.retType)) {
                throw new TypeMismatchError("Return type mismatch", it.pos);
            }
        }
        funcScope.haveRet = true;
        return null;
    }

    @Override
    public Void visit(BreakStmtNode it) {
        if (curScope.getLastloop() == null) {
            throw new InvalidControlFlowError("Break statement should be in loop", it.pos);
        }
        return null;
    }

    @Override
    public Void visit(ContinueStmtNode it) {
        if (curScope.getLastloop() == null) {
            throw new InvalidControlFlowError("Break statement should be in loop", it.pos);
        }
        return null;
    }

    @Override
    public Void visit(ExprStmtNode it) {
        it.expr.accept(this);
        return null;
    }

    @Override
    public Void visit(EmptyStmtNode it) {
        return null;
    }

}
