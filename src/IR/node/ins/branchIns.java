package IR.node.ins;

import IR.item.IRitem;
import IR.type.IRType;

public class branchIns extends IRIns {
    IRitem cond;
    String trueLabel;
    String falseLabel;


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
}
