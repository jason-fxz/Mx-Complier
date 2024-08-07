package AST.Node.expr;

import AST.ASTVisitor;
import Util.position;

public class BoolExprNode extends ExprNode {
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
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
    
}
