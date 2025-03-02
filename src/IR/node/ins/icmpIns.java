package IR.node.ins;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import IR.IRvisitor;
import IR.item.*;
import IR.type.IRType;

public class icmpIns extends IRIns {
    public IRvar result;
    public String op;
    public IRitem lhs, rhs;

    public icmpIns(IRvar result, String op, IRitem lhs, IRitem rhs) {
        if (!lhs.type.equals(rhs.type)) {
            throw new RuntimeException("icmpIns lhs and rhs type mismatch");
        }
        // if (!lhs.type.equals(IRType.IRIntType) && !lhs.type.equals(IRType.IRPtrType)) {
        //     throw new RuntimeException("icmpIns lhs and rhs should be IRIntType or IRPtrType");
        // }
        if (!op.equals("eq") && !op.equals("ne") && !op.equals("slt") && !op.equals("sgt") && !op.equals("sle")
                && !op.equals("sge")) {
            throw new RuntimeException("Invalid icmpIns op");
        }
        if (!result.type.equals(IRType.IRBoolType)) {
            throw new RuntimeException("icmpIns result should be IRBoolType");
        }
        this.result = result;
        this.op = op;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public String toString() {
        return result.toString() + " = icmp " + op + " " + lhs.type.toString() + " " + lhs.toString() + ", "
                + rhs.toString();
    }

    @Override
    public <T> T accecpt(IRvisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public void replaceUse(IRitem old, IRitem nw) {
        if (lhs.equals(old)) {
            lhs = nw;
        }
        if (rhs.equals(old)) {
            rhs = nw;
        }
    }

    @Override
    public void replaceUse(Map<IRitem, IRitem> map) {
        if (map.containsKey(lhs)) {
            lhs = map.get(lhs);
        }
        if (map.containsKey(rhs)) {
            rhs = map.get(rhs);
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
        // icmpIns doesn't have any label references
    }

    @Override
    public Set<IRvar> getUses() {
        Set<IRvar> res = new HashSet<>();
        if (lhs instanceof IRvar) {
            res.add((IRvar) lhs);
        }
        if (rhs instanceof IRvar) {
            res.add((IRvar) rhs);
        }
        return res;
    }

    @Override
    public IRvar getDef() {
        return result;
    }

    @Override
    public IRIns clone() {
        return new icmpIns((IRvar) result.clone(), op, lhs.clone(), rhs.clone());
    }

}