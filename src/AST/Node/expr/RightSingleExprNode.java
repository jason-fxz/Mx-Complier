package AST.Node.expr;

import AST.ASTVisitor;
import Util.position;

public class RightSingleExprNode extends ExprNode {
    public ExprNode lhs;
    public enum unaryrightOpType {
        inc, dec
    };
    unaryrightOpType op;

    public RightSingleExprNode(position pos, ExprNode left, unaryrightOpType op) {
        super(pos);
        this.lhs = left;
        this.op = op;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return lhs.toString() + " " + op.toString();
    }
    
}
