package AST.Node.expr;

import AST.ASTVisitor;
import Util.position;

public class IntExprNode extends LiteralExprNode {
    public int value;

    public IntExprNode(int value, position pos) {
        super(pos);
        this.value = value;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
    
}
