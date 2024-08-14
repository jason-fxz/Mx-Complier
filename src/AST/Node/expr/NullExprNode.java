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
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
    
}
