package IR.node.ins;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public List<IRvar> getUses() {
        List<IRvar> res = new ArrayList<>();
        res.add(pointer);
        return res;
    }

    @Override
    public IRvar getDef() {
        return result;
    }
    
}
