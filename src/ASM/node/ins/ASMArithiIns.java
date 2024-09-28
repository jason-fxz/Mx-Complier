package ASM.node.ins;

import ASM.ASMVisitor;
import ASM.item.ASMReg;


// op rd, rs1, rs2
// Arith operation 
public class ASMArithiIns extends ASMIns {
    private String op;
    private ASMReg rd, rs1;
    private int imm;

    public ASMArithiIns(String op, ASMReg rd, ASMReg rs1, int imm) {
        this.op = op;
        this.rd = rd;
        this.rs1 = rs1;
        this.imm = imm;
        assert imm >= -2048 && imm <= 2047;
    }

    @Override
    public String toString() {
        return String.format("%-8s", this.op) + this.rd + ", " + this.rs1 + ", " + this.imm;
    }

    @Override
    public <T> T accept(ASMVisitor<T> visitor) {
        return visitor.visit(this);
    }

    
    
}
