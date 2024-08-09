package AST.Node.stmt;

import java.util.ArrayList;

import AST.ASTVisitor;
import Util.position;

public class BlockStmtNode extends StmtNode {
    public ArrayList<StmtNode> stmts = new ArrayList<>();

    public BlockStmtNode(position pos) {
        super(pos);
    }

    @Override
    public String toString() {
        indentDepth++;
        String str = "{\n";
        for (StmtNode stmt : stmts) {
            str += stmt.toString() + "\n";
        }
        indentDepth--;
        str += super.toString() + "}";
        return str;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
