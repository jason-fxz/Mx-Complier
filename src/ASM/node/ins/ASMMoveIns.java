package ASM.node.ins;

import ASM.ASMVisitor;
import ASM.item.ASMReg;

// mv rd, rs
public class ASMMoveIns extends ASMIns {
    private ASMReg rd;
    private ASMReg rs;

    public ASMMoveIns(ASMReg rd, ASMReg rs) {
        this.rd = rd;
        this.rs = rs;
    }

    @Override
    public String toString() {
        return String.format("%-8s", "mv") + rd + ", " + rs;
    }

    @Override
    public <T> T accept(ASMVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
