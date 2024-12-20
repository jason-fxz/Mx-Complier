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

    public boolean isUseless() {
        return rd.equals(rs);
    }

    @Override
    public String toString() {
        if (isUseless()) return "";
        return String.format("%-8s", "mv") + rd + ", " + rs;
    }

    @Override
    public <T> T accept(ASMVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public int countBytes() {
        return 4;
    }
}
