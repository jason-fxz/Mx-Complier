package AST.Node.stmt;

import AST.ASTVisitor;
import AST.Node.expr.ExprNode;
import Util.position;

public class WhileStmtNode extends StmtNode {
    public ExprNode condition;
    public StmtNode body;

    public WhileStmtNode(position pos, ExprNode condition, StmtNode body) {
        super(pos);
        this.condition = condition;
        this.body = body;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        String str = "while (" + condition.toString() + ") ";
        if (body instanceof EmptyStmtNode) {
            str += ";";
        } else if (body instanceof BlockStmtNode) {
            str += " " + ((BlockStmtNode)body).toString0();
        } else {
            indentDepth++;
            str += "\n" + body.toString();
            indentDepth--;
        }
        return super.toString() + str;
    }
    
}
