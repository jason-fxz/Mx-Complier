package AST.Node.expr;

import AST.ASTVisitor;
import Util.position;

public class LeftSingleExprNode extends ExprNode {
    public ExprNode rhs;
    public enum unaryleftOpType {
        inc, dec, neg, not, bitnot
    };
    unaryleftOpType op;

    public LeftSingleExprNode(position pos, ExprNode right, unaryleftOpType op) {
        super(pos);
        this.rhs = right;
        this.op = op;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return op.toString() + " " + rhs.toString();
    }
    
}
