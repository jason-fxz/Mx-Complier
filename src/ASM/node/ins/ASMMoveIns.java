package ASM.node.ins;

import ASM.ASMVisitor;

// mv rd, rs
public class ASMMoveIns extends ASMIns {
    private String rd;
    private String rs;

    public ASMMoveIns(String rd, String rs) {
        this.rd = rd;
        this.rs = rs;
    }

    @Override
    public String toString() {
        return String.format("%-8s", "mv") + rd + ", " + rs;
    }

    @Override
    public <T> T accept(ASMVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
