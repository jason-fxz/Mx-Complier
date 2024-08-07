package AST.Node.expr;

import AST.ASTVisitor;
import Util.position;

public class NewExprNode extends ExprNode {
    public String name;
    public int dim = 0;
    public ArrayInitNode array = null;

    public NewExprNode(position pos, String name, int dim) {
        super(pos);
        this.name = name;
        this.dim = dim;
    }

    @Override
    public String toString() {
        return "new " + name + "[]".repeat(dim);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
    
}
