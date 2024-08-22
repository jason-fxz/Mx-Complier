package IR.node.ins;

import IR.IRvisitor;
import IR.item.IRvar;
import IR.type.IRType;

public class allocaIns extends IRIns {
    public IRvar result;
    public IRType type;

    public allocaIns(IRvar result, IRType type) {
        if (!result.type.equals(IRType.IRPtrType)) {
            throw new RuntimeException("allocaIns: result should be IRPtrType");
        }
        this.result = result;
        this.type = type;
    }

    @Override
    public String toString() {
        return result.toString() + " = alloca " + type.toString();
    }

    @Override
    public <T> T accecpt(IRvisitor<T> visitor) {
        return visitor.visit(this);
    }
}