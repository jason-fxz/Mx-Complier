package AST.Node.stmt;


import AST.ASTVisitor;
import AST.Node.def.VarsDefNode;
import Util.position;

// A list of variable definitions
public class VarDefStmtNode extends StmtNode {
    final public VarsDefNode varsDef;

    public VarDefStmtNode(position pos, VarsDefNode varsDef) {
        super(pos);
        this.varsDef = varsDef;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return super.toString() + varsDef.toString();
    }
    
}
