package IR.node.ins;

import IR.item.IRvar;

public class loadIns extends IRIns {
    IRvar result;
    IRvar pointer;

    public loadIns(IRvar result, IRvar pointer) {
        this.result = result;
        this.pointer = pointer;
    }

    @Override
    public String toString() {
        return result.toString() + " = load " + result.type.toString() + ", ptr " + pointer.toString();
    }
    
}
