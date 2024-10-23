package ASM.node.ins;

import ASM.ASMVisitor;
import ASM.item.ASMReg;

// li rd, imm
// Load a immediate value
public class ASMLoadImmIns extends ASMIns {
    private ASMReg rd;
    private int imm;

    public ASMLoadImmIns(ASMReg rd, int imm) {
        this.rd = rd;
        this.imm = imm;
    }

    @Override
    public String toString() {
        if (-2048 <= imm && imm <= 2047) return String.format("%-8s", "addi") + rd + ", zero, " + imm;
        return String.format("%-8s", "li") + rd + ", " + imm;
    }

    @Override
    public <T> T accept(ASMVisitor<T> visitor) {
        return visitor.visit(this);
    }
    
    @Override
    public int countBytes() {
        return (-2048 <= imm && imm <= 2047) ? 4 : 8;
    }
}
