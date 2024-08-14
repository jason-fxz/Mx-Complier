package AST.Node.expr;

import AST.ASTVisitor;
import Util.position;

public class StringExprNode extends LiteralExprNode {
    public String value;

    public StringExprNode(position pos, String value) {
        super(pos);
        this.value = value;
    }

    @Override
    public String toString() {
        return "\"" + value + "\"";
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
    
}
