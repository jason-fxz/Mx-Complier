package AST.Node.stmt;

import AST.ASTVisitor;
import AST.Node.expr.ExprNode;
import Util.position;

public class ReturnStmtNode extends StmtNode {
    public ExprNode expr;

    public ReturnStmtNode(position pos, ExprNode expr) {
        super(pos);
        this.expr = expr;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        if (expr == null) {
            return super.toString() + "return ;";
        } else {
            return super.toString() + "return " + expr.toString() + ";";
        }
    }
    
}
