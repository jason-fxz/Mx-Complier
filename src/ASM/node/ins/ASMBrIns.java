package ASM.node.ins;

import ASM.ASMVisitor;
import ASM.item.ASMReg;

// beqz rs, label
// Branch to label if rs == 0
public class ASMBrIns extends ASMIns {
    private ASMReg rs1;
    private ASMReg rs2;
    private String op;
    private String label;

    public ASMBrIns(ASMReg rs1, ASMReg rs2, String op, String label) {
        if (!op.equals("beq") && !op.equals("bne") && !op.equals("blt") && !op.equals("bge")) {
            throw new RuntimeException("Invalid ASMBrIns op " + op);
        }
        this.rs1 = rs1;
        this.rs2 = rs2;
        this.op = op;
        this.label = label;
    }

    @Override
    public String toString() {
        return String.format("%-8s", op) + rs1 + ", " + rs2 + ", " + label;
    }

    @Override
    public <T> T accept(ASMVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
