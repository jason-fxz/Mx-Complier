package Frontend;

import Grammar.MxParser;
import Grammar.MxParserBaseVisitor;

import Util.position;
import Util.error.*;
import Util.info.*;

import org.antlr.v4.runtime.tree.TerminalNode;

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
        ctx.children.forEach(Def -> {
            if (!(Def instanceof TerminalNode)) {
                root.Defs.add((DefNode) visit(Def));
            }
        });
        return root;
    }

    @Override
    public ASTNode visitVarDef(MxParser.VarDefContext ctx) {
        VarsDefNode varsDef = new VarsDefNode(new position(ctx));
        TypeInfo type = new TypeInfo(ctx.typeName());
        ctx.varConstruct().forEach(varConstruct -> {
            VarDefNode vardef = new VarDefNode(new position(ctx),
                    varConstruct.Identifier().getText(),
                    type,
                    null);
            if (varConstruct.expr() != null) {
                vardef.init = (ExprNode) visit(varConstruct.expr());
            }
            varsDef.varDefs.add(vardef);
        });
        return varsDef;
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
            if (!classDef.constructor.name.equals(classDef.name)) {
                throw new SemanticError("class constructor name should be same as class name",
                        new position(ctx.classConstruct(0)));
            }
        } else if (ctx.classConstruct().size() > 1) {
            throw new MultipleDefinitionsError("class constructor Defs more than once",
                    new position(ctx.classConstruct(1)));
        }
        ctx.varDef().forEach(varDef -> {
            classDef.varsDefs.add((VarsDefNode) visit(varDef));
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
        BinaryExprNode.binaryOpType op = BinaryExprNode.binaryOpType.fromString(ctx.op.getText());
        return new BinaryExprNode(new position(ctx), lhs, rhs, op);
    }

    @Override
    public ASTNode visitLeftExpr(MxParser.LeftExprContext ctx) {
        ExprNode rhs = (ExprNode) visit(ctx.expr());
        LeftSingleExprNode.unaryleftOpType op = LeftSingleExprNode.unaryleftOpType.fromString(ctx.op.getText());
        return new LeftSingleExprNode(new position(ctx), rhs, op);
    }

    @Override
    public ASTNode visitRightExpr(MxParser.RightExprContext ctx) {
        ExprNode lhs = (ExprNode) visit(ctx.expr());
        RightSingleExprNode.unaryrightOpType op = RightSingleExprNode.unaryrightOpType.fromString(ctx.op.getText());
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
        } else if (ctx.StringLiteral() != null) {
            return new StringExprNode(new position(ctx),
                    ctx.StringLiteral().getText().substring(1, ctx.StringLiteral().getText().length() - 1));
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

    @Override
    public ASTNode visitConditionExpr(MxParser.ConditionExprContext ctx) {
        return new ConditionExprNode(new position(ctx), (ExprNode) visit(ctx.cond), (ExprNode) visit(ctx.then),
                (ExprNode) visit(ctx.else_));
    }


    // turn $$ to $
    private String fmtstr(String str, int headoffset, int tailoffset) {
        return str.substring(headoffset, str.length() - tailoffset).replace("$$", "$");
    }

    @Override
    public ASTNode visitFormatStrExpr(MxParser.FormatStrExprContext ctx) {
        FmtStringExprNode fmtexpr = new FmtStringExprNode(new position(ctx));
        if (ctx.FormatStrI() != null) {
            fmtexpr.strlist.add(fmtstr(ctx.FormatStrI().getText(), 2, 1));
        } else {
            fmtexpr.strlist.add(fmtstr(ctx.FormatStrL().getText(), 2, 1));
            fmtexpr.exprlist.add((ExprNode) visit(ctx.expr(0)));
            for (int i = 0; i < ctx.FormatStrM().size(); ++i) {
                fmtexpr.strlist.add(fmtstr(ctx.FormatStrM(i).getText(), 1, 1));
                fmtexpr.exprlist.add((ExprNode) visit(ctx.expr(i + 1)));
            }
            fmtexpr.strlist.add(fmtstr(ctx.FormatStrR().getText(), 1, 1));
        }
        return visitChildren(ctx);
    }

    @Override
    public ASTNode visitArrayInitial(MxParser.ArrayInitialContext ctx) {
        ArrayInitNode arrayinit = new ArrayInitNode(new position(ctx));
        ctx.exprList().expr().forEach(expr -> {
            ExprNode child = (ExprNode) visit(expr);
            if (!(child instanceof ArrayExprNode) && !(child instanceof LiteralExprNode)) {
                throw new SemanticError("Array Init invaild expr", new position(ctx));
            }
            arrayinit.exprs.add(child);
        });
        return arrayinit;
    }

    @Override
    public ASTNode visitNewVarExpr(MxParser.NewVarExprContext ctx) {
        NewVarExprNode newexpr = new NewVarExprNode(new position(ctx), ctx.type().getText());
        return newexpr;
    }

    @Override
    public ASTNode visitNewArrayExpr(MxParser.NewArrayExprContext ctx) {
        NewArrayExprNode newexpr = new NewArrayExprNode(new position(ctx), ctx.type().getText(),
                ctx.arrayUnit().size());
        boolean flag = true;
        for (int i = 0; i < ctx.arrayUnit().size(); ++i) {
            if (ctx.arrayUnit(i).expr() != null) {
                if (flag == false) {
                    throw new SemanticError("new array size invalid", new position(ctx));
                }
                newexpr.dimsize.add((ExprNode) visit(ctx.arrayUnit(i).expr()));
            } else {
                flag = false;
                newexpr.dimsize.add(null);
            }
        }
        if (ctx.arrayInitial() != null) {
            // System.err.println("new array init");
            newexpr.array = (ArrayInitNode) visit(ctx.arrayInitial());
        }
        return newexpr;
    }

    @Override
    public ASTNode visitBlockStmt(MxParser.BlockStmtContext ctx) {
        BlockStmtNode blockstmt = new BlockStmtNode(new position(ctx));
        ctx.stmt().forEach(stmt -> {
            blockstmt.stmts.add((StmtNode) visit(stmt));
        });
        return blockstmt;
    }

    @Override
    public ASTNode visitIfStmt(MxParser.IfStmtContext ctx) {
        ExprNode cond_ = (ExprNode) visit(ctx.cond);
        StmtNode then_ = (StmtNode) visit(ctx.then);
        StmtNode else_ = ctx.else_ != null ? (StmtNode) visit(ctx.else_) : null;
        return new IfStmtNode(new position(ctx), cond_, then_, else_);
    }

    @Override
    public ASTNode visitForStmt(MxParser.ForStmtContext ctx) {
        StmtNode init = ctx.init != null ? (StmtNode) visit(ctx.init) : null;
        ExprNode cond = ctx.cond != null ? (ExprNode) visit(ctx.cond) : null;
        ExprNode step = ctx.step != null ? (ExprNode) visit(ctx.step) : null;
        StmtNode body = (StmtNode) visit(ctx.body);
        return new ForStmtNode(new position(ctx), init, cond, step, body);
    }

    @Override
    public ASTNode visitWhileStmt(MxParser.WhileStmtContext ctx) {
        ExprNode cond = ctx.cond != null ? (ExprNode) visit(ctx.cond) : null;
        StmtNode body = ctx.body != null ? (StmtNode) visit(ctx.body) : null;
        return new WhileStmtNode(new position(ctx), cond, body);
    }

    @Override
    public ASTNode visitJumpStmt(MxParser.JumpStmtContext ctx) {
        if (ctx.Break() != null) {
            return new BreakStmtNode(new position(ctx));
        } else if (ctx.Continue() != null) {
            return new ContinueStmtNode(new position(ctx));
        } else if (ctx.Return() != null) {
            if (ctx.expr() != null) {
                return new ReturnStmtNode(new position(ctx), (ExprNode) visit(ctx.expr()));
            } else {
                return new ReturnStmtNode(new position(ctx), null);
            }
        } else
            throw new SyntaxError("Invalid JumpStmt", new position(ctx));
    }

    @Override
    public ASTNode visitVarDefStmt(MxParser.VarDefStmtContext ctx) {
        return new VarDefStmtNode(new position(ctx), (VarsDefNode) visit(ctx.varDef()));
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
    // return visitChildren(ctx);
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

    // @Override
    // public ASTNode visitArrayInitExpr(MxParser.ArrayInitExprContext ctx) {
    // return visitChildren(ctx);
    // }

    // @Override
    // public ASTNode visitFormatStringExpr(MxParser.FormatStringExprContext ctx) {
    // return visitChildren(ctx);
    // }

    // @Override
    // public ASTNode visitLiterExpr(MxParser.LiterExprContext ctx) {
    // return visitChildren(ctx);
    // }

    @Override
    public ASTNode visitParenExpr(MxParser.ParenExprContext ctx) {
        return visit(ctx.expr());
    }

    // @Override
    // public ASTNode visitStmt(MxParser.StmtContext ctx) {
    // return visitChildren(ctx);
    // }

}
