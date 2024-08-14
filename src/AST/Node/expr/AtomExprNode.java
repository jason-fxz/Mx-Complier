package AST.Node.expr;

import AST.ASTVisitor;
import Util.position;

public class AtomExprNode extends ExprNode {
    public String name;


    public AtomExprNode(position pos, String name) {
        super(pos);
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }



}
