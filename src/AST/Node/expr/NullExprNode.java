package AST.Node.expr;

import AST.ASTVisitor;
import Util.position;

public class NullExprNode extends LiteralExprNode {
    public NullExprNode(position pos) {
        super(pos);
    }

    @Override
    public String toString() {
        return "null";
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
    
}
