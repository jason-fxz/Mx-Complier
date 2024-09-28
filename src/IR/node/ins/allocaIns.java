package IR.node.ins;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import IR.IRvisitor;
import IR.item.IRitem;
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

    @Override
    public void replaceUse(IRitem old, IRitem nw) {
    }

    @Override
    public void replaceUse(Map<IRitem, IRitem> map) {
    }

    @Override
    public Set<IRvar> getUses() {
        return new HashSet<>();
    }

    @Override
    public IRvar getDef() {
        return result;
    }
}