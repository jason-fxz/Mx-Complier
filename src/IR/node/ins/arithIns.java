package IR.node.ins;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import IR.IRvisitor;
import IR.item.*;
import IR.type.IRType;

public class arithIns extends IRIns {
    public IRvar result;
    public String op;
    public IRitem lhs, rhs;

    public arithIns(IRvar result, String op, IRitem lhs, IRitem rhs) {
        if (!lhs.type.equals(rhs.type)) {
            throw new RuntimeException("ArithIns lhs and rhs type mismatch");
        }
        if (!lhs.type.equals(IRType.IRIntType) && !lhs.type.equals(IRType.IRBoolType)) {
            throw new RuntimeException("ArithIns lhs and rhs should be IRIntType or IRBoolType");
        }
        if (!op.equals("add") && !op.equals("sub") && !op.equals("mul") && !op.equals("sdiv") && !op.equals("srem")
                && !op.equals("shl") && !op.equals("ashr") && !op.equals("and") && !op.equals("or")
                && !op.equals("xor")) {
            throw new RuntimeException("Invalid ArithIns op");
        }

        this.result = result;
        this.op = op;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public String toString() {
        return result.toString() + " = " + op + " " + lhs.type.toString() + " " + lhs.toString() + ", "
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
        // No labels in arithIns, so nothing to replace
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
        return new arithIns((IRvar)result.clone(), op, lhs.clone(), rhs.clone());
    }
}