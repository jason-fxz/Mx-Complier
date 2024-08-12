package Frontend;

import AST.ASTVisitor;
import AST.Node.RootNode;
import AST.Node.def.*;
import AST.Node.expr.*;
import AST.Node.stmt.*;
import Util.scope.globalScope;
import Util.info.*;
import Util.BuiltinElements;

public class SemanticCollector implements ASTVisitor {
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
    public void visit(RootNode it) {
        it.Defs.forEach(sd -> {
            if (!(sd instanceof VarsDefNode)) {
                sd.accept(this);
            }
        });
        
    }

    @Override
    public void visit(ClassDefNode it) {
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
    }

    @Override
    public void visit(FuncDefNode it) {
        FuncInfo funcInfo = new FuncInfo(it.name, it.type, it.pos);
        it.params.forEach(par -> {
            funcInfo.AddArguement(par.type);
        });
        gScope.DefFunc(it.name, funcInfo);
    }

    @Override
    public void visit(VarsDefNode it) {
        it.varDefs.forEach(var -> {
            gScope.DefVar(var.name, var.type, var.pos);
        });
    }

    @Override
    public void visit(VarDefNode it) {
    }

    @Override
    public void visit(BinaryExprNode it) {
    }

    @Override
    public void visit(LeftSingleExprNode it) {
    }

    @Override
    public void visit(RightSingleExprNode it) {
    }

    @Override
    public void visit(ConditionExprNode it) {
    }

    @Override
    public void visit(AssignExprNode it) {
    }

    @Override
    public void visit(AtomExprNode it) {
    }

    @Override
    public void visit(MemberExprNode it) {
    }

    @Override
    public void visit(FuncExprNode it) {
    }

    @Override
    public void visit(NewVarExprNode it) {
    }

    @Override
    public void visit(NewArrayExprNode it) {
    }

    @Override
    public void visit(ArrayInitNode it) {
    }

    @Override
    public void visit(ArrayExprNode it) {
    }

    @Override
    public void visit(IntExprNode it) {
    }

    @Override
    public void visit(BoolExprNode it) {
    }

    @Override
    public void visit(StringExprNode it) {
    }

    @Override
    public void visit(NullExprNode it) {
    }

    @Override
    public void visit(FmtStringExprNode it) {
    }

    @Override
    public void visit(BlockStmtNode it) {
    }

    @Override
    public void visit(VarDefStmtNode it) {
    }

    @Override
    public void visit(IfStmtNode it) {
    }

    @Override
    public void visit(ForStmtNode it) {
    }

    @Override
    public void visit(WhileStmtNode it) {
    }

    @Override
    public void visit(ReturnStmtNode it) {
    }

    @Override
    public void visit(BreakStmtNode it) {
    }

    @Override
    public void visit(ContinueStmtNode it) {
    }

    @Override
    public void visit(ExprStmtNode it) {
    }

    @Override
    public void visit(EmptyStmtNode it) {
    }

}
