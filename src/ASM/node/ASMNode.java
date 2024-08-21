package ASM.node;

import ASM.ASMVisitor;

public abstract class ASMNode {
    public abstract String toString();

    public abstract <T> T accept(ASMVisitor<T> visitor);
}
