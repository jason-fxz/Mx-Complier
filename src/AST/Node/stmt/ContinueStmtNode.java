package AST.Node.stmt;

import AST.ASTVisitor;
import Util.position;

public class ContinueStmtNode extends StmtNode {
    public ContinueStmtNode(position pos) {
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return super.toString() + "continue;";
    }
    
}
