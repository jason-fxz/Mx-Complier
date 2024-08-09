package AST.Node.stmt;

import AST.ASTVisitor;
import AST.Node.expr.ExprNode;
import Util.position;

public class ForStmtNode extends StmtNode {
    public StmtNode init;
    public ExprNode cond;
    public ExprNode step;
    public StmtNode body;

    public ForStmtNode(position pos, StmtNode init, ExprNode cond, ExprNode step, StmtNode body) {
        super(pos);
        this.init = init;
        this.cond = cond;
        this.step = step;
        this.body = body;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        String str = "for (";
        if (init != null) {
            str += init.toString().substring(indentDepth * 4);
        }
        str += " ";
        if (cond != null) {
            str += cond.toString();
        }
        str += "; ";
        if (step != null) {
            str += step.toString();
        }
        str += ") ";
        if (!(body instanceof EmptyStmtNode)) {
            if (body instanceof BlockStmtNode) {
                str += body.toString();
            } else {
                indentDepth++;
                str += "\n" + body.toString();
                indentDepth--;
            }
        } else {
            str += ";";
        }
        return super.toString() + str;
    }
    
}
