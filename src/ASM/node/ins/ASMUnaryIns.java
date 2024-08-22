package ASM.node.ins;

import ASM.ASMVisitor;
import ASM.item.ASMReg;

public class ASMUnaryIns extends ASMIns {
    private String op;
    private ASMReg rd, rs;

    public ASMUnaryIns(String op, ASMReg rd, ASMReg rs) {
        this.op = op;
        this.rd = rd;
        this.rs = rs;
    }

    @Override
    public String toString() {
        return String.format("%-8s", this.op) + this.rd + ", " + this.rs;
    }

    @Override
    public <T> T accept(ASMVisitor<T> visitor) {
        return visitor.visit(this);
    }
    
}
