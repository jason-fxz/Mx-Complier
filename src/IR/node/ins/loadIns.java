package IR.node.ins;

import IR.IRvisitor;
import IR.item.IRvar;

public class loadIns extends IRIns {
    public IRvar result;
    public IRvar pointer;

    public loadIns(IRvar result, IRvar pointer) {
        this.result = result;
        this.pointer = pointer;
    }

    @Override
    public String toString() {
        return result.toString() + " = load " + result.type.toString() + ", ptr " + pointer.toString();
    }

    @Override
    public <T> T accecpt(IRvisitor<T> visitor) {
        return visitor.visit(this);
    }
    
}
