package AST.Node;

import Util.position;
import java.util.ArrayList;

import AST.ASTVisitor;
import AST.Node.def.*;

public class RootNode extends ASTNode {
    public ArrayList<DefNode> Defs = new ArrayList<>();

    public RootNode(position pos) {
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (DefNode Def : Defs) {
            sb.append(Def.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

}
