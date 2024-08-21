package ASM.node.ins;

import ASM.ASMVisitor;

// j offset
// jump to the label
public class ASMJumpIns extends ASMIns {
    private String label;

    public ASMJumpIns(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return String.format("%-8s", "j") + label;
    }

    @Override
    public <T> T accept(ASMVisitor<T> visitor) {
        return visitor.visit(this);
    }
    
}
