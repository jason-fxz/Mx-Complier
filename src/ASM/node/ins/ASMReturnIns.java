package ASM.node.ins;

import ASM.ASMVisitor;

public class ASMReturnIns extends ASMIns {
    @Override
    public String toString() {
        return "ret";
    }

    @Override
    public <T> T accept(ASMVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public int countBytes() {
        return 4;
    }
    
}
