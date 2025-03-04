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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IRvar) {
            return name.equals(((IRvar) obj).name);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
    
    public boolean isGlobal() {
        return name.startsWith("@");
    }

    public boolean isLocal() {
        return name.startsWith("%");
    }

    @Override
    public IRitem clone() {
        return new IRvar(type, new String(name));
    }
}
