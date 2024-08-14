package AST.Node.expr;

import AST.ASTVisitor;
import Util.position;

public class ArrayExprNode extends ExprNode {
    public ExprNode array, index;

    public ArrayExprNode(position pos, ExprNode array, ExprNode index) {
        super(pos);
        this.array = array;
        this.index = index;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return array.toString() + "[" + index.toString() + "]";
    }
}
