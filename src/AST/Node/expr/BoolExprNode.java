package AST.Node.expr;

import AST.ASTVisitor;
import Util.position;

public class BoolExprNode extends LiteralExprNode {
    public boolean value;

    public BoolExprNode(position pos, boolean value) {
        super(pos);
        this.value = value;
    }

    @Override
    public String toString() {
        return Boolean.toString(value);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
    
}
