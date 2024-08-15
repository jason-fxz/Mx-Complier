package IR.node.ins;

import IR.item.IRvar;
import IR.type.IRType;

public class allocaIns extends IRIns {
    IRType type;
    IRvar result;

    public allocaIns(String result, IRType type) {
        this.result = new IRvar(type, result);
        this.type = type;
    }

    @Override
    public String toString() {
        return result.name + " = alloca " + type.toString();
    }
}