package AST.Node.stmt;

import AST.ASTVisitor;
import AST.Node.expr.ExprNode;
import Util.position;

public class ExprStmtNode extends StmtNode {
    public ExprNode expr;
    public ExprStmtNode(position pos, ExprNode expr) {
        super(pos);
        this.expr = expr;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return super.toString() + expr.toString() + ";";
    }
    
}
