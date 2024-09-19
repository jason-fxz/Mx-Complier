package IR.node.ins;

import java.util.Map;

import IR.IRvisitor;
import IR.item.IRitem;
import IR.item.IRvar;

// store <ty> <value>, ptr <pointer>
public class storeIns extends IRIns {
    public IRitem value;
    public IRvar pointer;

    public storeIns(IRitem value, IRvar pointer) {
        this.value = value;
        this.pointer = pointer;
    }

    @Override
    public String toString() {
        return "store " + value.type.toString() + " " + value.toString() + ", ptr " + pointer.toString();
    }

    @Override
    public <T> T accecpt(IRvisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public void replaceUse(IRitem old, IRitem nw) {
        if (value.equals(old)) {
            value = nw;
        }
        if (pointer.equals(old)) {
            pointer = (IRvar) nw;
        }
    }

    @Override
    public void replaceUse(Map<IRitem, IRitem> map) {
        if (map.containsKey(value)) {
            value = map.get(value);
        }
        if (map.containsKey(pointer)) {
            pointer = (IRvar) map.get(pointer);
        }
    }

}
