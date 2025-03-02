package IR.node.ins;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

    @Override
    public void replaceDef(IRvar old, IRvar nw) {
        // No definition in store instruction
    }

    @Override
    public void replaceDef(Map<IRitem, IRitem> map) {
        // No definition in store instruction
    }

    @Override
    public void replaceLabel(Map<String, String> map) {
        // No labels in store instruction
    }

    @Override
    public Set<IRvar> getUses() {
        Set<IRvar> res = new HashSet<>();
        if (value instanceof IRvar) {
            if (((IRvar) value).isLocal()) res.add((IRvar) value);
        }
        if (pointer.isLocal()) res.add(pointer);
        return res;
    }

    @Override
    public IRvar getDef() {
        return null;
    }

    @Override
    public storeIns clone() {
        return new storeIns(value.clone(), (IRvar) pointer.clone());
    }
}
