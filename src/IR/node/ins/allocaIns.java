package IR.node.ins;

import IR.IRvisitor;
import IR.item.IRvar;

public class allocaIns extends IRIns {
    IRvar result;

    public allocaIns(IRvar result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return result.toString() + " = alloca " + result.type.toString();
    }

    @Override
    public <T> T accecpt(IRvisitor<T> visitor) {
        return visitor.visit(this);
    }
}