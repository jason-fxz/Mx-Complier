package IR.node.ins;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import IR.IRvisitor;
import IR.item.IRitem;
import IR.item.IRvar;
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

    @Override
    public void replaceUse(IRitem old, IRitem nw) {
        if (value.equals(old)) {
            value = nw;
        }
    }

    @Override
    public void replaceUse(Map<IRitem, IRitem> map) {
        if (map.containsKey(value)) {
            value = map.get(value);
        }
    }

    @Override
    public List<IRvar> getUses() {
        List<IRvar> res = new ArrayList<>();
        if (value instanceof IRvar) {
            res.add((IRvar) value);
        }
        return res;
    }

    @Override
    public IRvar getDef() {
        return null;
    }
    
    
}
