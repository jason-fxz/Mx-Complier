package ASM.item;

public class ASMAddr {
    private ASMReg base;
    private int offset;

    public ASMAddr(ASMReg base, int offset) {
        this.base = base;
        this.offset = offset;
    }

    @Override
    public String toString() {
        return offset + "(" + base + ")";
    }

    public ASMReg getBase() {
        return base;
    }

    public int getOffset() {
        return offset;
    }

}
