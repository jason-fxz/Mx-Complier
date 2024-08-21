package ASM.node.ins;

import ASM.item.ASMReg;

// la rd, label
// Load absolute address
public class ASMLoadAddrIns extends ASMIns {
    private ASMReg rd;
    private String label;

    public ASMLoadAddrIns(ASMReg rd, String label) {
        this.rd = rd;
        this.label = label;
    }

    @Override
    public String toString() {
        return String.format("%-8s", "la") + rd + ", " + label;
    }
    
}
