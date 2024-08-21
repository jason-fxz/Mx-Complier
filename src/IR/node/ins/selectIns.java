package IR.node.ins;

import IR.item.IRvar;
import IR.type.IRType;
import IR.IRvisitor;
import IR.item.IRitem;

public class selectIns extends IRIns {
    IRvar result;
    IRitem cond;
    IRitem value1;
    IRitem value2;

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
        return result.toString() + " = select i1 " + cond.toString() + ", " + value1.type.toString() + " "
                + value1.toString() + ", " + value2.type.toString() +  " " + value2.toString();
    }

    @Override
    public <T> T accecpt(IRvisitor<T> visitor) {
        return visitor.visit(this);
    }
}
