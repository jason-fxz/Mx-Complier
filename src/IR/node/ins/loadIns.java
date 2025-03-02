package IR.node.ins;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import IR.IRvisitor;
import IR.item.IRitem;
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

    @Override
    public void replaceUse(IRitem old, IRitem nw) {
        if (pointer.equals(old)) {
            pointer = (IRvar) nw;
        }
    }

    @Override
    public void replaceUse(Map<IRitem, IRitem> map) {
        if (map.containsKey(pointer)) {
            pointer = (IRvar) map.get(pointer);
        }
    }

    @Override
    public void replaceDef(IRvar old, IRvar nw) {
        if (result.equals(old)) {
            result = nw;
        }
    }

    @Override
    public void replaceDef(Map<IRitem, IRitem> map) {
        if (map.containsKey(result)) {
            result = (IRvar)map.get(result);
        }
    }

    @Override
    public void replaceLabel(Map<String, String> map) {
        // No labels to replace in loadIns
    }

    @Override
    public Set<IRvar> getUses() {
        Set<IRvar> res = new HashSet<>();
        if (pointer.isLocal()) res.add(pointer);
        return res;
    }

    @Override
    public IRvar getDef() {
        return result;
    }
    
    @Override
    public loadIns clone() {
        return new loadIns((IRvar) result.clone(), (IRvar) pointer.clone());
    }
}
