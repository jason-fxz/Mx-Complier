package ASM.node.ins;

import ASM.item.ASMReg;

// beqz rs, label
// Branch to label if rs == 0
public class ASMBeqzIns extends ASMIns {
    private ASMReg rs;
    private String label;

    public ASMBeqzIns(ASMReg rs, String label) {
        this.rs = rs;
        this.label = label;
    }

    @Override
    public String toString() {
        return String.format("%-8s", "beqz") + rs + ", " + label;
    }
}
