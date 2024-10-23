package ASM.node.ins;

import ASM.ASMVisitor;
import ASM.item.ASMAddr;
import ASM.item.ASMReg;

// lw rd imm(base)
public class ASMLoadIns extends ASMIns {
    private ASMReg rd;
    private ASMAddr addr;
    private String symbol;

    public ASMLoadIns(ASMReg rd, ASMAddr addr) {
        this.rd = rd;
        this.addr = addr;
        this.symbol = null;
        assert addr.getOffset() >= -2048 && addr.getOffset() <= 2047;
    }

    public ASMLoadIns(ASMReg rd, String symbol) {
        this.rd = rd;
        this.symbol = symbol;
        this.addr = null;
    }

    @Override
    public String toString() {
        return String.format("%-8s", "lw") + rd + ", " + (addr != null ? addr : symbol);
    }

    @Override
    public <T> T accept(ASMVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public int countBytes() {
        return addr != null ? 4 : 8;
    }
}