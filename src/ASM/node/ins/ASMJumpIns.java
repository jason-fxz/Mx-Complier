package ASM.node.ins;

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
    
}
