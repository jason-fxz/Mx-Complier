package ASM.node.ins;

import ASM.ASMVisitor;
import ASM.node.ASMNode;

public abstract class ASMIns extends ASMNode {
    @Override
    public abstract String toString();

    @Override
    public abstract <T> T accept(ASMVisitor<T> visitor);

    public abstract int countBytes();
    
}
