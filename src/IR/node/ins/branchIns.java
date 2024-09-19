package IR.node.ins;

import java.util.Map;

import IR.IRvisitor;
import IR.item.IRitem;
import IR.type.IRType;

public class branchIns extends IRIns {
    public IRitem cond;
    public String trueLabel;
    public String falseLabel;


    public branchIns(IRitem cond, String trueLabel, String falseLabel) {
        if (!cond.type.equals(IRType.IRBoolType)) {
            throw new RuntimeException("branchIns cond should be IRBoolType");
        }
        this.cond = cond;
        this.trueLabel = trueLabel;
        this.falseLabel = falseLabel;
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
}
