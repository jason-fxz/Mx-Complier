package ASM.node.ins;

// call label 
public class ASMCallIns extends ASMIns {
    private String label;

    public ASMCallIns(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return String.format("%-8s", "call") + label;
    }
    
}
