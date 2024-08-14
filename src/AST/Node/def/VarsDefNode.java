package AST.Node.def;

import java.util.ArrayList;

import AST.ASTVisitor;
import Util.position;

public class VarsDefNode extends DefNode {
    public ArrayList<VarDefNode> varDefs = new ArrayList<>();

    public VarsDefNode(position pos) {
        super(pos);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(varDefs.get(0).type.GetTypeName());
        str.append(" ");
        for (VarDefNode varDef : varDefs) {
            str.append(varDef.name);
            if (varDef.init != null) {
                str.append(" = ");
                str.append(varDef.init.toString());
            }
            str.append(", ");
        }
        if (varDefs.size() >= 1) {
            str.delete(str.length() - 2, str.length());
        }
        return str.toString() + ";";
    }
    
}
