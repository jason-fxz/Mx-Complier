package AST.Node.stmt;

import java.util.ArrayList;

import AST.ASTVisitor;
import AST.Node.def.VarDefNode;
import Util.position;

// A list of variable definitions
public class VarDefStmtNode extends StmtNode {
    final public ArrayList<VarDefNode> vardefs = new ArrayList<>();

    public VarDefStmtNode(position pos) {
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        String str = super.toString();
        for (VarDefNode vardef : vardefs) {
            str += vardef.toString() + "; ";
        }
        return str;
    }
    
}
