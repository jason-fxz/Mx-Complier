package IR.node.ins;

import IR.item.IRitem;
import IR.item.IRvar;

public class storeIns extends IRIns {
    IRitem value;
    IRvar pointer;

    public storeIns(IRitem value, IRvar pointer) {
        this.value = value;
        this.pointer = pointer;
    }

    @Override
    public String toString() {
        return "store " + value.type.toString() + value.toString() + ", ptr " + pointer.type.toString();
    }

}
