package Frontend;

import AST.*;
import AST.Node.*;
import AST.Node.def.*;
import AST.Node.expr.*;
import AST.Node.stmt.*;
import Util.scope.*;
import Util.BuiltinElements;
import Util.error.*;
import Util.info.ExprInfo;
import Util.info.TypeInfo;

public class SemanticChecker implements ASTVisitor {

    private Scope curScope;
    private globalScope gScope;
    private boolean haveMain = false;

    private void enterScope(Scope scope) {
        curScope = scope;
    }

    private void exitScope() {
        curScope = curScope.parScope();
    }

    private Scope newScope(Scope parScope) {
        return new Scope(parScope);
    }

    private boolean checkTypeValid(TypeInfo type) {
        return (type.isBasic && !type.isVoid) || (gScope.haveClass(type.typeName));
    }

    public SemanticChecker(globalScope gScope) {
        this.curScope = this.gScope = gScope;
        this.haveMain = false;
    }

    @Override
    public void visit(RootNode it) {
        it.Defs.forEach(sd -> sd.accept(this));
        if (!haveMain) {
            throw new SemanticError("No main function", it.pos);
        }
    }

    @Override
    public void visit(ClassDefNode it) {
        Scope classScope = newScope(curScope);
        enterScope(classScope);
        if (it.constructor != null) {
            it.constructor.accept(this);
        }
        for (var funcDef : it.funcDefs) {
            funcDef.accept(this);
        }
        exitScope();
    }

    @Override
    public void visit(FuncDefNode it) {
        Scope funcScope = newScope(curScope);
        enterScope(funcScope);
        // put parameters into scope
        it.params.forEach(par -> {
            curScope.DefVar(par.name, par.type, par.pos);
        });
        for (var stmt : it.body.stmts) {
            stmt.accept(this);
        }
        // check main function
        if (it.name.equals("main")) {
            if (!it.type.equals(BuiltinElements.intType)) {
                throw new SemanticError("Main function should return int", it.pos);
            }
            if (it.params.size() != 0) {
                throw new SemanticError("Main function should have no parameters", it.pos);
            }
            haveMain = true;
        }
        exitScope();
    }

    @Override
    public void visit(VarDefNode it) {
        if (!checkTypeValid(it.type)) {
            throw new SemanticError("Invalid type " + it.type.GetTypeName(), it.pos);
        }
        if (curScope.haveVar(it.name, false)) {
            throw new MultipleDefinitionsError("Variable " + it.name + " already defined", it.pos);
        }
        if (it.init != null) {
            it.init.accept(this);
            if (!it.init.info.equals(it.type)) {
                throw new SemanticError("Cannot assign " + it.init.info.GetTypeName() + " to " + it.type.GetTypeName(),
                        it.pos);
            }
        }
        curScope.DefVar(it.name, it.type, it.pos);
    }

    @Override
    public void visit(VarsDefNode it) {
        for (var varDef : it.varDefs) {
            varDef.accept(this);
        }
    }

    @Override
    public void visit(BinaryExprNode it) {
        it.lhs.accept(this);
        it.rhs.accept(this);
        var lhsinfo = it.lhs.info;
        var rhsinfo = it.rhs.info;
        // TODO null type check
        if (lhsinfo.isFunc) {
            throw new SemanticError("Cannot perform binary operation on function " + lhsinfo.GetTypeName(), it.pos);
        }
        if (rhsinfo.isFunc) {
            throw new SemanticError("Cannot perform binary operation on function " + rhsinfo.GetTypeName(), it.pos);
        }
        if (!lhsinfo.equals(rhsinfo)) {
            throw new SemanticError("Cannot perform binary operation on different types " + lhsinfo.GetTypeName()
                    + " and " + rhsinfo.GetTypeName(), it.pos);
        }
        if (lhsinfo.dim == 0) { // non-array
            if (lhsinfo.equals(BuiltinElements.intType)) { // int type
                if (it.op.in("&&", "||")) { // logic op
                    throw new SemanticError("Operator " + it.op + " is not supported for int", it.pos);
                } else if (it.op.in("==", "!=", "<", ">", "<=", ">=")) { // camp op
                    it.info = new ExprInfo("bool", false);
                } else { // arith op / bit op
                    it.info = new ExprInfo("int", false);
                }
            } else if (lhsinfo.equals(BuiltinElements.boolType)) {
                if (it.op.in("==", "!=", "&&", "||")) {
                    it.info = new ExprInfo("bool", false);
                } else {
                    throw new SemanticError("Operator " + it.op + " is not supported for bool", it.pos);
                }
            } else if (lhsinfo.equals(BuiltinElements.stringType)) {
                if (it.op.equals("+")) { // str concat
                    it.info = new ExprInfo("string", false);
                } else if (it.op.in("==", "!=", "<", ">", "<=", ">=")) {
                    it.info = new ExprInfo("bool", false);
                } else {
                    throw new SemanticError("Operator " + it.op + " is not supported for string", it.pos);
                }
            } else { // class
                if (it.op.in("==", "!=")) {
                    it.info = new ExprInfo("bool", false);
                } else {
                    throw new SemanticError("Operator " + it.op + " is not supported for class", it.pos);
                }
            }
        } else { // array
            if (it.op.in("==", "!=")) {
                it.info = new ExprInfo("bool", false);
            } else {
                throw new SemanticError("Operator " + it.op + " is not supported for array", it.pos);
            }
        }
    }

    @Override
    public void visit(LeftSingleExprNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(RightSingleExprNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(ConditionExprNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(AssignExprNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(AtomExprNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(MemberExprNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(FuncExprNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(NewVarExprNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(NewArrayExprNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(ArrayInitNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(ArrayExprNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(IntExprNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(BoolExprNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(StringExprNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(NullExprNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(FmtStringExprNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(BlockStmtNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(VarDefStmtNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(IfStmtNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(ForStmtNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(WhileStmtNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(ReturnStmtNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(BreakStmtNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(ContinueStmtNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(ExprStmtNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(EmptyStmtNode it) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

}
