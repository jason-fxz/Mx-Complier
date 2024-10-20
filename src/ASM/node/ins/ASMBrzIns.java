package ASM.node.ins;

import ASM.ASMVisitor;
import ASM.item.ASMReg;

// beqz rs, label
// Branch to label if rs == 0
public class ASMBrzIns extends ASMIns {
    private ASMReg rs;
    private String label;
    private String op;

    public ASMBrzIns(ASMReg rs, String op, String label) {
        if (!op.equals("ge") && !op.equals("le") && !op.equals("gt") && !op.equals("lt") && !op.equals("eq") && !op.equals("ne")) {
            throw new RuntimeException("Invalid op in ASMBrzIns");
        }
        this.rs = rs;
        this.label = label;
        this.op = op;
    }

    @Override
    public String toString() {
        return String.format("%-8s", String.format("b%sz", op)) + rs + ", " + label;
    }

    @Override
    public <T> T accept(ASMVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
