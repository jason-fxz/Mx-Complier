package ASM.node.ins;

import ASM.ASMVisitor;
import ASM.item.ASMReg;


// op rd, rs1, rs2
// Arith operation 
public class ASMArithIns extends ASMIns {
    private String op;
    private ASMReg rd, rs1, rs2;

    public ASMArithIns(String op, ASMReg rd, ASMReg rs1, ASMReg rs2) {
        this.op = op;
        this.rd = rd;
        this.rs1 = rs1;
        this.rs2 = rs2;
    }

    @Override
    public String toString() {
        return String.format("%-8s", this.op) + this.rd + ", " + this.rs1 + ", " + this.rs2;
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
