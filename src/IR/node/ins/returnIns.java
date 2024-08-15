package IR.node.ins;

import IR.item.IRitem;
import IR.type.IRType;

public class returnIns extends IRIns {
    IRitem value;
    public returnIns(IRitem value) {
        this.value = value;
    }

    @Override
    public String toString() {
        if (value.type.equals((IRType.IRvoidType))) return "ret void";
        return "ret " + value.type.toString() + " " + value.toString();
    }
    
}
