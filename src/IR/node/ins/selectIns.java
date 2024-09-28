package IR.node.ins;

import IR.item.IRvar;
import IR.type.IRType;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import IR.IRvisitor;
import IR.item.IRitem;

public class selectIns extends IRIns {
    public IRvar result;
    public IRitem cond;
    public IRitem value1;
    public IRitem value2;

    public selectIns(IRvar result, IRitem cond, IRitem value1, IRitem value2) {
        if (!value1.type.equals(value2.type)) {
            throw new RuntimeException("selectIns: value1 and value2 should have the same type");
        }
        if (!result.type.equals(value1.type)) {
            throw new RuntimeException("selectIns: result and value1 should have the same type");
        }
        if (!cond.type.equals(IRType.IRBoolType)) {
            throw new RuntimeException("selectIns: cond should be bool type");
        }
        this.result = result;
        this.cond = cond;
        this.value1 = value1;
        this.value2 = value2;
    }

    @Override
    public String toString() {
        throw new RuntimeException("selectIns is now not supported in IR");
        // return result.toString() + " = select i1 " + cond.toString() + ", " + value1.type.toString() + " "
        //         + value1.toString() + ", " + value2.type.toString() +  " " + value2.toString();
    }

    @Override
    public <T> T accecpt(IRvisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public void replaceUse(IRitem old, IRitem nw) {
        if (cond.equals(old)) {
            cond = nw;
        }
        if (value1.equals(old)) {
            value1 = nw;
        }
        if (value2.equals(old)) {
            value2 = nw;
        }
    }

    @Override
    public void replaceUse(Map<IRitem, IRitem> map) {
        if (map.containsKey(cond)) {
            cond = map.get(cond);
        }
        if (map.containsKey(value1)) {
            value1 = map.get(value1);
        }
        if (map.containsKey(value2)) {
            value2 = map.get(value2);
        }
    }

    @Override
    public Set<IRvar> getUses() {
        Set<IRvar> res = new HashSet<>();
        if (cond instanceof IRvar) {
            res.add((IRvar) cond);
        }
        if (value1 instanceof IRvar) {
            res.add((IRvar) value1);
        }
        if (value2 instanceof IRvar) {
            res.add((IRvar) value2);
        }
        return res;
    }

    @Override
    public IRvar getDef() {
        return result;
    }
}
