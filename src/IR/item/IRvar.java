package IR.item;

import AST.Node.def.VarDefNode;
import IR.type.IRType;
import Util.IRLabeler;

public class IRvar extends IRitem{
    public String name;

    public IRvar(IRType type, String name) {
        super(type);
        this.name = name;
        if (!name.startsWith("@") && !name.startsWith("%")) {
            throw new RuntimeException("IRvar name should start with @ or %");
        }
    }

    public IRvar(VarDefNode varDefNode, boolean isGlobal) {
        super(new IRType(varDefNode.type));
        if (isGlobal) {
            this.name = "@" + varDefNode.name;
        } else {
            this.name = "%" + varDefNode.name;
        }
    }

    @Override
    public String toString() {
        return name;
    }
    

}
