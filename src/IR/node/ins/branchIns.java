package IR.node.ins;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import IR.IRvisitor;
import IR.item.IRitem;
import IR.item.IRvar;
import IR.type.IRType;

public class branchIns extends IRIns {
    public IRitem cond;
    public String trueLabel;
    public String falseLabel;
    public boolean likely = true;

    public branchIns(IRitem cond, String trueLabel, String falseLabel) {
        if (!cond.type.equals(IRType.IRBoolType)) {
            throw new RuntimeException("branchIns cond should be IRBoolType");
        }
        this.cond = cond;
        this.trueLabel = trueLabel;
        this.falseLabel = falseLabel;
    }

    public branchIns(IRitem cond, String trueLabel, String falseLabel, boolean likely) {
        if (!cond.type.equals(IRType.IRBoolType)) {
            throw new RuntimeException("branchIns cond should be IRBoolType");
        }
        this.cond = cond;
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
        return "br i1 " + cond.toString() + ", label %" + trueLabel + ", label %" + falseLabel;
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
    }

    @Override
    public void replaceUse(Map<IRitem, IRitem> map) {
        if (map.containsKey(cond)) {
            cond = map.get(cond);
        }
    }

    @Override
    public void replaceDef(IRvar old, IRvar nw) {
        // No definition in branch instruction
    }

    @Override
    public void replaceDef(Map<IRitem, IRitem> map) {
        // No definition in branch instruction
    }

    @Override
    public void replaceLabel(Map<String, String> map) {
        if (map.containsKey(trueLabel)) {
            trueLabel = map.get(trueLabel);
        }
        if (map.containsKey(falseLabel)) {
            falseLabel = map.get(falseLabel);
        }
    }

    @Override
    public Set<IRvar> getUses() {
        Set<IRvar> res = new HashSet<>();
        if (cond instanceof IRvar) {
            res.add((IRvar) cond);
        }
        return res;
    }

    @Override
    public IRvar getDef() {
        return null;
    }
    
    @Override
    public IRIns clone() {
        return new branchIns(cond.clone(), new String(trueLabel), new String(falseLabel), likely);
    }
}
