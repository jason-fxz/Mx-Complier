package ASM.node.ins;

import ASM.item.ASMAddr;
import ASM.item.ASMReg;

// lw rd imm(base)
public class ASMLoadIns extends ASMIns {
    private ASMReg rd;
    private ASMAddr addr;

    public ASMLoadIns(ASMReg rd, ASMAddr addr) {
        this.rd = rd;
        this.addr = addr;
    }

    @Override
    public String toString() {
        return String.format("%-8s", "lw") + rd + ", " + addr;
    }
}