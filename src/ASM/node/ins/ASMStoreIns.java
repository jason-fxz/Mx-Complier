package ASM.node.ins;

import ASM.ASMVisitor;
import ASM.item.ASMAddr;
import ASM.item.ASMReg;

// sw rs imm(base)
public class ASMStoreIns extends ASMIns {
    private ASMReg rs;
    private ASMAddr addr;

    public ASMStoreIns(ASMReg rs, ASMAddr addr) {
        this.rs = rs;
        this.addr = addr;
    }

    @Override
    public String toString() {
        return String.format("%-8s", "sw") + rs + ", " + addr;
    }

    @Override
    public <T> T accept(ASMVisitor<T> visitor) {
        return visitor.visit(this);
    }
    
}
