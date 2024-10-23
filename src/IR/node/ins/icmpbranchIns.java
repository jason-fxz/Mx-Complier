package IR.node.ins;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import IR.IRvisitor;
import IR.item.IRitem;
import IR.item.IRvar;
import IR.type.IRType;
import Util.IRLabeler;

public class icmpbranchIns extends IRIns {
    public IRitem lhs;
    public IRitem rhs;
    public String op;
    public String trueLabel;
    public String falseLabel;
    public boolean likely = true;

    private IRvar llvmirTmpVar;

    public icmpbranchIns(IRitem lhs, IRitem rhs, String op, String trueLabel, String falseLabel) {
        if (!op.equals("eq") && !op.equals("ne") && !op.equals("slt") && !op.equals("sgt") && !op.equals("sle")
                && !op.equals("sge")) {
            throw new RuntimeException("Invalid icmpbranchIns op");
        }

        if (!lhs.type.equals(rhs.type)) {
            throw new RuntimeException("icmpbranchIns lhs and rhs type mismatch");
        }
        this.llvmirTmpVar = new IRvar(IRType.IRBoolType, IRLabeler.getIdLabel("%icmpbr"));
        this.lhs = lhs;
        this.rhs = rhs;
        this.op = op;
        this.trueLabel = trueLabel;
        this.falseLabel = falseLabel;
    }

    public icmpbranchIns(icmpIns icmp, String trueLabel, String falseLabel) {
        this.llvmirTmpVar = icmp.result;
        this.lhs = icmp.lhs;
        this.rhs = icmp.rhs;
        this.op = icmp.op;
        this.trueLabel = trueLabel;
        this.falseLabel = falseLabel;
    }

    public icmpbranchIns(icmpIns icmp, String trueLabel, String falseLabel, boolean likely) {
        this.llvmirTmpVar = icmp.result;
        this.lhs = icmp.lhs;
        this.rhs = icmp.rhs;
        this.op = icmp.op;
        this.trueLabel = trueLabel;
        this.falseLabel = falseLabel;
        this.likely = likely;
    }

    public void replaceLabel(String old, String nw) {
        if (trueLabel.equals(old)) {
            trueLabel = nw;
        }
        if (falseLabel.equals(old)) {
            falseLabel = nw;
        }
    }

    @Override
    public String toString() {
        return llvmirTmpVar.toString() + " = icmp " + op + " " + lhs.type.toString() + " " + lhs.toString() + ", "
                + rhs.toString() + "\n  " + 
        "br i1 " + llvmirTmpVar.toString() + ", label %" + trueLabel + ", label %" + falseLabel;
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
        return null;
    }
}
