package ASM.node.ins;

import ASM.ASMVisitor;
import ASM.item.ASMReg;
import IR.node.ins.returnIns;

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
        return String.format("%-8s", "li") + rd + ", " + imm;
    }

    @Override
    public <T> T accept(ASMVisitor<T> visitor) {
        return visitor.visit(this);
    }
    
}
