package ASM.node.ins;

import ASM.ASMVisitor;
import ASM.item.ASMAddr;
import ASM.item.ASMReg;

// sw rs imm(base)
public class ASMStoreIns extends ASMIns {
    private ASMReg rs;
    private ASMAddr addr;
    private ASMReg rt;
    private String symbol;

    public ASMStoreIns(ASMReg rs, ASMAddr addr) {
        this.rs = rs;
        this.addr = addr;
        this.rt = null;
        this.symbol = null;
        assert addr.getOffset() >= -2048 && addr.getOffset() <= 2047;
    }

    public ASMStoreIns(ASMReg rs, String symbol, ASMReg rt) {
        this.rs = rs;
        this.symbol = symbol;
        this.rt = rt;
        this.addr = null;
    }

    

    @Override
    public String toString() {
        if (addr != null) return String.format("%-8s", "sw") + rs + ", " + addr;
        else return String.format("%-8s", "sw") + rs + ", " + symbol + ", " + rt;
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
