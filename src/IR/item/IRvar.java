package IR.item;

import AST.Node.def.VarDefNode;
import IR.type.IRType;

public class IRvar extends IRitem {
    public String name;

    public IRvar(IRType type, String name) {
        super(type);
        this.name = name;
        if (!name.startsWith("@") && !name.startsWith("%")) {
            throw new RuntimeException("IRvar name should start with @ or %");
        }
    }

    public IRvar(String ptrName) {
        super(IRType.IRPtrType);
        this.name = ptrName;
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
