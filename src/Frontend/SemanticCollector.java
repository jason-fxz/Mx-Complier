package Frontend;

import AST.ASTVisitor;
import AST.Node.RootNode;
import AST.Node.def.*;
import AST.Node.expr.*;
import AST.Node.stmt.*;
import Util.scope.globalScope;
import Util.info.*;
import Util.BuiltinElements;

public class SemanticCollector implements ASTVisitor<Void> {
    private globalScope gScope;

    public SemanticCollector(globalScope gScope) {
        this.gScope = gScope;
        for (FuncInfo func : BuiltinElements.BuiltinFuncs) {
            gScope.DefFunc(func.label, func);
        }
        for (ClassInfo classInfo : BuiltinElements.BuiltinClasses) {
            gScope.DefClass(classInfo.label, classInfo);
        }
    }

    @Override
    public Void visit(RootNode it) {
        it.Defs.forEach(sd -> {
            if (!(sd instanceof VarsDefNode)) {
                sd.accept(this);
            }
        });
        return null;
    }

    @Override
    public Void visit(ClassDefNode it) {
        ClassInfo classInfo = new ClassInfo(it.name, it.pos);

        it.varsDefs.forEach(varsdefs -> {
            varsdefs.varDefs.forEach(var -> {
                classInfo.AddMember(var.name, var.type, var.pos);
            });
        });
        it.funcDefs.forEach(funcdef -> {
            FuncInfo funcInfo = new FuncInfo(funcdef.name, funcdef.type, funcdef.pos);
            funcdef.params.forEach(par -> {
                funcInfo.AddArguement(par.type);
            });
            classInfo.AddMethod(funcdef.name, funcInfo, funcdef.pos);
        });

        if (it.constructor != null) {
            FuncInfo funcInfo = new FuncInfo(it.constructor.name, it.constructor.type, it.constructor.pos);
            classInfo.AddMethod(it.constructor.name, funcInfo, it.constructor.pos);
        }

        gScope.DefClass(it.name, classInfo);

        return null;
    }

    @Override
    public Void visit(FuncDefNode it) {
        FuncInfo funcInfo = new FuncInfo(it.name, it.type, it.pos);
        it.params.forEach(par -> {
            funcInfo.AddArguement(par.type);
        });
        gScope.DefFunc(it.name, funcInfo);
        return null;
    }

    @Override
    public Void visit(VarsDefNode it) {
        it.varDefs.forEach(var -> {
            gScope.DefVar(var.name, var.type, var.pos);
        });
        return null;
    }

    @Override
    public Void visit(VarDefNode it) {
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public Void visit(BinaryExprNode it) {
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public Void visit(LeftSingleExprNode it) {
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public Void visit(RightSingleExprNode it) {
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public Void visit(ConditionExprNode it) {
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public Void visit(AssignExprNode it) {
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public Void visit(AtomExprNode it) {
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public Void visit(MemberExprNode it) {
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public Void visit(FuncExprNode it) {
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public Void visit(NewVarExprNode it) {
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public Void visit(NewArrayExprNode it) {
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public Void visit(ArrayInitNode it) {
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public Void visit(ArrayExprNode it) {
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public Void visit(IntExprNode it) {
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public Void visit(BoolExprNode it) {
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public Void visit(StringExprNode it) {
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public Void visit(NullExprNode it) {
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public Void visit(FmtStringExprNode it) {
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public Void visit(BlockStmtNode it) {
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public Void visit(VarDefStmtNode it) {
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public Void visit(IfStmtNode it) {
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public Void visit(ForStmtNode it) {
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public Void visit(WhileStmtNode it) {
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public Void visit(ReturnStmtNode it) {
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public Void visit(BreakStmtNode it) {
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public Void visit(ContinueStmtNode it) {
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public Void visit(ExprStmtNode it) {
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public Void visit(EmptyStmtNode it) {
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

}
