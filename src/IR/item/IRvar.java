package IR.item;

import AST.Node.def.VarDefNode;
import IR.type.IRType;

public class IRvar extends IRitem{
    public String name;

    public IRvar(IRType type, String name) {
        super(type);
        this.name = name;
        if (!name.startsWith("@") && !name.startsWith("%")) {
            throw new RuntimeException("IRvar name should start with @ or %");
        }
    }

    public IRvar(VarDefNode varDefNode) {
        super(new IRType(varDefNode.type));
        this.name = "%" + varDefNode.name;
    }

    @Override
    public String toString() {
        return name;
    }
    

}
