package AST.Node.stmt;

import AST.ASTVisitor;
import Util.position;

public class EmptyStmtNode extends StmtNode {
    public EmptyStmtNode(position pos) {
        super(pos);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return super.toString() + ";";
    }
    
}
