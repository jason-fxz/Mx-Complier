package IR.node.ins;

import IR.item.IRvar;
import IR.type.IRType;

public class allocaIns extends IRIns {
    IRvar result;

    public allocaIns(IRvar result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return result.toString() + " = alloca " + result.type.toString();
    }
}