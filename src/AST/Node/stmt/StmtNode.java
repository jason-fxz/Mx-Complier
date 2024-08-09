package AST.Node.stmt;

import AST.Node.ASTNode;
import Util.position;

public abstract class StmtNode extends ASTNode {
    public static int indentDepth = 0;

    public StmtNode(position pos) {
        super(pos);
    }

    @Override
    public String toString() {
        return "    ".repeat(indentDepth);
    }

    


}