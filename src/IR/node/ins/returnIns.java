package IR.node.ins;

import IR.IRvisitor;
import IR.item.IRitem;
import IR.type.IRType;

public class returnIns extends IRIns {
    public IRitem value;
    public returnIns(IRitem value) {
        this.value = value;
    }

    @Override
    public String toString() {
        if (value.type.equals((IRType.IRvoidType))) return "ret void";
        return "ret " + value.type.toString() + " " + value.toString();
    }

    @Override
    public <T> T accecpt(IRvisitor<T> visitor) {
        return visitor.visit(this);
    }
    
}
