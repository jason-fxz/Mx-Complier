package AST.Node.expr;

import AST.ASTVisitor;
import Util.position;

public class BinaryExprNode extends ExprNode {
    public ExprNode lhs;
    public ExprNode rhs;
    public enum binaryOpType {
        mul, div, mod, add, sub, shl, shr, lt, gt, le, ge, eq, ne, and, or, bitand, bitor, bitxor
    }
    public binaryOpType op;

    public BinaryExprNode(position pos, ExprNode left, ExprNode right, binaryOpType op) {
        super(pos);
        this.lhs = left;
        this.rhs = right;
        this.op = op;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return lhs.toString() + " " + op.toString() + " " + rhs.toString();
    }
    
}
