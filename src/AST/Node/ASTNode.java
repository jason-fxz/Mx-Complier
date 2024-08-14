package AST.Node;

import AST.ASTVisitor;
import Util.position;
import Util.scope.Scope;

abstract public class ASTNode {
    public position pos;
    public Scope scope = null;

    public ASTNode(position pos) {
        this.pos = pos;
    }

    abstract public String toString();

    abstract public <T> T accept(ASTVisitor<T> visitor);
}
