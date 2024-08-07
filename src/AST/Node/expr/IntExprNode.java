package AST.Node.expr;

import AST.ASTVisitor;
import Util.position;

public class IntExprNode extends ExprNode {
    public int value;

    public IntExprNode(int value, position pos) {
        super(pos);
        this.value = value;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
    
}
