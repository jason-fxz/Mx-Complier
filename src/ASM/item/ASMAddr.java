package ASM.item;

public class ASMAddr extends ASMItem {
    private ASMReg base;
    private int offset;

    public ASMAddr(ASMReg base, int offset) {
        super(false);
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ASMAddr) {
            ASMAddr addr = (ASMAddr) obj;
            return base.equals(addr.base) && offset == addr.offset;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return base.hashCode() ^ offset;
    }

}
