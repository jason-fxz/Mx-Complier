package IR.node.ins;

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
}