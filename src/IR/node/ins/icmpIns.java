package IR.node.ins;

import IR.item.*;
import IR.type.IRType;

public class icmpIns extends IRIns {
    IRvar result;
    String op;
    IRitem lhs, rhs;

    public icmpIns(IRvar result, String op, IRitem lhs, IRitem rhs) {
        if (!lhs.type.equals(rhs.type)) {
            throw new RuntimeException("icmpIns lhs and rhs type mismatch");
        }
        if (!lhs.type.equals(IRType.IRIntType)) {
            throw new RuntimeException("icmpIns lhs and rhs should be IRIntType");
        }
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
}