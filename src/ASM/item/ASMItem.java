package ASM.item;

public abstract class ASMItem {
    boolean inReg;
    public ASMItem(boolean inReg) {
        this.inReg = inReg;
    }
    public boolean inReg() {
        return inReg;
    }
    public boolean inMem() {
        return !inReg;
    }
    @Override
    public abstract String toString();
    @Override
    public abstract boolean equals(Object obj);
    @Override
    public abstract int hashCode();
}
