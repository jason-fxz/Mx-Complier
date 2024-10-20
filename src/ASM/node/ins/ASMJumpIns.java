package ASM.node.ins;

import ASM.ASMVisitor;
import ASM.item.ASMReg;
import Optimize.SCCP;

// j offset
// jump to the label / rs
public class ASMJumpIns extends ASMIns {
    private String label;
    private ASMReg rs;

    public ASMJumpIns(String label) {
        this.label = label;
    }

    public ASMJumpIns(ASMReg rs) {
        this.rs = rs;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        if (rs != null) {
            return String.format("%-8s", "jr") + rs;
        } else {
            return String.format("%-8s", "j") + label;
        }
    }

    @Override
    public <T> T accept(ASMVisitor<T> visitor) {
        return visitor.visit(this);
    }
    
}
