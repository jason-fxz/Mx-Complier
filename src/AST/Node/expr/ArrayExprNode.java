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
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return array.toString() + "[" + index.toString() + "]";
    }
}
