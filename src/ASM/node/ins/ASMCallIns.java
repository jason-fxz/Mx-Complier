package ASM.node.ins;

import ASM.ASMVisitor;

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

    @Override
    public <T> T accept(ASMVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public int countBytes() {
        return 8;
    }
    
}
