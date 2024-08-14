package AST.Node.expr;

import AST.ASTVisitor;
import Util.position;

public class AssignExprNode extends ExprNode {
    public ExprNode lhs;
    public ExprNode rhs;
    public enum assignOpType {
        assign
    }
    public assignOpType op;

    public AssignExprNode(position pos, ExprNode left, ExprNode right, assignOpType op) {
        super(pos);
        this.lhs = left;
        this.rhs = right;
        this.op = op;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return lhs.toString() + " = " + rhs.toString();
    }
    
}
