package Frontend;

import Grammar.MxParser;
import Grammar.MxParserBaseVisitor;

import Util.position;
import Util.error.MultipleDefinitionsError;
import Util.error.SemanticError;
import Util.info.*;

import AST.Node.*;
import AST.Node.def.*;
import AST.Node.expr.*;
import AST.Node.stmt.*;

public class ASTBuilder extends MxParserBaseVisitor<ASTNode> {

    public ASTBuilder() {

    }

    @Override
    public ASTNode visitProgram(MxParser.ProgramContext ctx) {
        RootNode root = new RootNode(new position(ctx));
        ctx.varDef().forEach(varDef -> {
            root.varDefs.add((VarDefStmtNode) visit(varDef));
        });
        ctx.funcDef().forEach(funcDef -> {
            root.funcDefs.add((FuncDefNode) visit(funcDef));
        });
        ctx.classDef().forEach(classDef -> {
            root.classDefs.add((ClassDefNode) visit(classDef));
        });
        return root;
    }

    @Override
    public ASTNode visitVarDef(MxParser.VarDefContext ctx) {
        VarDefStmtNode varDefstmt = new VarDefStmtNode(new position(ctx));
        TypeInfo type = new TypeInfo(ctx.typeName());

        ctx.varConstruct().forEach(varConstruct -> {
            VarDefNode vardef = new VarDefNode(new position(ctx),
                    varConstruct.Identifier().getText(),
                    type,
                    null);
            if (varConstruct.expr() != null) {
                vardef.init = (ExprNode) visit(varConstruct.expr());
            }
            varDefstmt.vardefs.add(vardef);
        });
        return varDefstmt;
    }

    @Override
    public ASTNode visitFuncDef(MxParser.FuncDefContext ctx) {
        FuncDefNode funcDef = new FuncDefNode(new position(ctx), (BlockStmtNode) visit(ctx.body));
        funcDef.name = ctx.Identifier().getText();
        funcDef.type = new TypeInfo(ctx.typeName());
        // funcDef.params.add
        if (ctx.funcParamList() != null) {
            ctx.funcParamList().funcParam().forEach(parameter -> {
                funcDef.params.add(new VarDefNode(new position(ctx),
                        parameter.varConstruct().Identifier().getText(),
                        new TypeInfo(parameter.typeName()),
                        null));
            });
        }
        return funcDef;
    }

    @Override
    public ASTNode visitClassDef(MxParser.ClassDefContext ctx) {
        ClassDefNode classDef = new ClassDefNode(new position(ctx), ctx.Identifier().getText());
        if (ctx.classConstruct().size() == 1) {
            classDef.constructor = (FuncDefNode) visit(ctx.classConstruct(0));
            if (classDef.constructor.name != classDef.name) {
                throw new SemanticError("class constructor name should be same as class name",
                        new position(ctx.classConstruct(0)));
            }
        } else if (ctx.classConstruct().size() > 1) {
            throw new MultipleDefinitionsError("class constructor Defs more than once",
                    new position(ctx.classConstruct(1)));
        }
        ctx.varDef().forEach(varDef -> {
            classDef.varDefs.add((VarDefStmtNode) visit(varDef));
        });
        ctx.funcDef().forEach(funcDef -> {
            classDef.funcDefs.add((FuncDefNode) visit(funcDef));
        });
        return classDef;
    }

    @Override
    public ASTNode visitClassConstruct(MxParser.ClassConstructContext ctx) {
        FuncDefNode constructor = new FuncDefNode(new position(ctx), (BlockStmtNode) visit(ctx.blockStmt()));
        constructor.name = ctx.Identifier().getText();
        constructor.type = new TypeInfo("void");
        return constructor;
    }

    @Override
    public ASTNode visitBinaryExpr(MxParser.BinaryExprContext ctx) {
        ExprNode lhs = (ExprNode) visit(ctx.lhs);
        ExprNode rhs = (ExprNode) visit(ctx.rhs);
        BinaryExprNode.binaryOpType op = null;
        switch (ctx.op.getText()) {
            case "*":
                op = BinaryExprNode.binaryOpType.mul;
                break;
            case "/":
                op = BinaryExprNode.binaryOpType.div;
                break;
            case "%":
                op = BinaryExprNode.binaryOpType.mod;
                break;
            case "+":
                op = BinaryExprNode.binaryOpType.add;
                break;
            case "-":
                op = BinaryExprNode.binaryOpType.sub;
                break;
            case "<<":
                op = BinaryExprNode.binaryOpType.shl;
                break;
            case ">>":
                op = BinaryExprNode.binaryOpType.shr;
                break;
            case "<":
                op = BinaryExprNode.binaryOpType.lt;
                break;
            case ">":
                op = BinaryExprNode.binaryOpType.gt;
                break;
            case "<=":
                op = BinaryExprNode.binaryOpType.le;
                break;
            case ">=":
                op = BinaryExprNode.binaryOpType.ge;
                break;
            case "==":
                op = BinaryExprNode.binaryOpType.eq;
                break;
            case "!=":
                op = BinaryExprNode.binaryOpType.ne;
                break;
            case "&&":
                op = BinaryExprNode.binaryOpType.and;
                break;
            case "||":
                op = BinaryExprNode.binaryOpType.or;
                break;
            case "&":
                op = BinaryExprNode.binaryOpType.bitand;
                break;
            case "|":
                op = BinaryExprNode.binaryOpType.bitor;
                break;
            case "^":
                op = BinaryExprNode.binaryOpType.bitxor;
                break;
            default:
                throw new SemanticError("unknown binary operator", new position(ctx));
        }
        return new BinaryExprNode(new position(ctx), lhs, rhs, op);
    }

    @Override
    public ASTNode visitLeftExpr(MxParser.LeftExprContext ctx) {
        ExprNode rhs = (ExprNode) visit(ctx.expr());
        LeftSingleExprNode.unaryleftOpType op = null;
        switch (ctx.op.getText()) {
            case "++":
                op = LeftSingleExprNode.unaryleftOpType.inc;
                break;
            case "--":
                op = LeftSingleExprNode.unaryleftOpType.dec;
                break;
            case "!":
                op = LeftSingleExprNode.unaryleftOpType.not;
                break;
            case "~":
                op = LeftSingleExprNode.unaryleftOpType.bitnot;
                break;
            case "-":
                op = LeftSingleExprNode.unaryleftOpType.neg;
                break;
            default:
                throw new SemanticError("unknown left operator", new position(ctx));
        }
        return new LeftSingleExprNode(new position(ctx), rhs, op);
    }

    @Override
    public ASTNode visitRightExpr(MxParser.RightExprContext ctx) {
        ExprNode lhs = (ExprNode) visit(ctx.expr());
        RightSingleExprNode.unaryrightOpType op = null;
        switch (ctx.op.getText()) {
            case "++":
                op = RightSingleExprNode.unaryrightOpType.inc;
                break;
            case "--":
                op = RightSingleExprNode.unaryrightOpType.dec;
                break;
            default:
                throw new SemanticError("unknown right operator", new position(ctx));
        }
        return new RightSingleExprNode(new position(ctx), lhs, op);
    }

    @Override
    public ASTNode visitAtomExpr(MxParser.AtomExprContext ctx) {
        return new AtomExprNode(new position(ctx), ctx.getText());
    }

    @Override
    public ASTNode visitMemberExpr(MxParser.MemberExprContext ctx) {
        return new MemberExprNode(new position(ctx), (ExprNode) visit(ctx.expr()), ctx.Identifier().getText());
    }

    @Override
    public ASTNode visitAssignExpr(MxParser.AssignExprContext ctx) {
        ExprNode lhs = (ExprNode) visit(ctx.lhs);
        ExprNode rhs = (ExprNode) visit(ctx.rhs);
        AssignExprNode.assignOpType op = null;
        switch (ctx.op.getText()) {
            case "=":
                op = AssignExprNode.assignOpType.assign;
                break;
            default:
                throw new SemanticError("unknown assign operator", new position(ctx));
        }
        return new AssignExprNode(new position(ctx), lhs, rhs, op);
    }

    @Override
    public ASTNode visitFuncExpr(MxParser.FuncExprContext ctx) {
        FuncExprNode funexpr = new FuncExprNode(new position(ctx), (ExprNode) visit(ctx.expr()));
        if (ctx.exprList() != null) {
            ctx.exprList().expr().forEach(expr -> {
                funexpr.args.add((ExprNode) visit(expr));
            });
        }
        return funexpr;
    }

    @Override
    public ASTNode visitExprStmt(MxParser.ExprStmtContext ctx) {
        return new ExprStmtNode(new position(ctx), (ExprNode) visit(ctx.expr()));
    }

    @Override
    public ASTNode visitLiteralExpr(MxParser.LiteralExprContext ctx) {
        if (ctx.IntegerLiteral() != null) {
            return new IntExprNode(Integer.parseInt(ctx.IntegerLiteral().getText()), new position(ctx));
        } else if (ctx.StringLiteral() != null){
            return new StringExprNode(new position(ctx), ctx.StringLiteral().getText());
        } else if (ctx.Null() != null) {
            return new NullExprNode(new position(ctx));
        } else if (ctx.True() != null) {
            return new BoolExprNode(new position(ctx), true);
        } else if (ctx.False() != null) {
            return new BoolExprNode(new position(ctx), false);
        } else {
            throw new SemanticError("unknown literal", new position(ctx));
        }    
    }

    @Override
    public ASTNode visitEmptyStmt(MxParser.EmptyStmtContext ctx) {
        return new EmptyStmtNode(new position(ctx));
    }

    @Override
    public ASTNode visitArrayExpr(MxParser.ArrayExprContext ctx) {
        return new ArrayExprNode(new position(ctx), (ExprNode) visit(ctx.array), (ExprNode) visit(ctx.index));
    }

    // @Override
    // public ASTNode visitFuncParamList(MxParser.FuncParamListContext ctx) {
    // return visitChildren(ctx);
    // }

    // @Override
    // public ASTNode visitFuncParam(MxParser.FuncParamContext ctx) {
    // return visitChildren(ctx);
    // }

    // @Override
    // public ASTNode visitVarConstruct(MxParser.VarConstructContext ctx) {
    // return visitChildren(ctx);
    // }


    // @Override
    // public ASTNode visitExprList(MxParser.ExprListContext ctx) {
    //     return visitChildren(ctx);
    // }

    // @Override
    // public ASTNode visitTypeName(MxParser.TypeNameContext ctx) {
    // return visitChildren(ctx);
    // }

    // @Override
    // public ASTNode visitType(MxParser.TypeContext ctx) {
    // return visitChildren(ctx);
    // }

    // @Override
    // public ASTNode visitBaseType(MxParser.BaseTypeContext ctx) {
    // return visitChildren(ctx);
    // }

    // @Override
    // public ASTNode visitArrayUnit(MxParser.ArrayUnitContext ctx) {
    // return visitChildren(ctx);
    // }

    @Override
    public ASTNode visitArrayInitExpr(MxParser.ArrayInitExprContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public ASTNode visitNewVarExpr(MxParser.NewVarExprContext ctx) {
        return visitChildren(ctx);
    }

    

   

    @Override
    public ASTNode visitNewArrayExpr(MxParser.NewArrayExprContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public ASTNode visitFormatStringExpr(MxParser.FormatStringExprContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public ASTNode visitConditionExpr(MxParser.ConditionExprContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public ASTNode visitLiterExpr(MxParser.LiterExprContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public ASTNode visitParenExpr(MxParser.ParenExprContext ctx) {
        return visitChildren(ctx);
    }

    

    @Override
    public ASTNode visitArrayInitial(MxParser.ArrayInitialContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public ASTNode visitFormatStrExpr(MxParser.FormatStrExprContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public ASTNode visitStmt(MxParser.StmtContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public ASTNode visitBlockStmt(MxParser.BlockStmtContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public ASTNode visitIfStmt(MxParser.IfStmtContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public ASTNode visitForStmt(MxParser.ForStmtContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public ASTNode visitWhileStmt(MxParser.WhileStmtContext ctx) {
        return visitChildren(ctx);
    }

    

    @Override
    public ASTNode visitJumpStmt(MxParser.JumpStmtContext ctx) {
        return visitChildren(ctx);
    }

}
