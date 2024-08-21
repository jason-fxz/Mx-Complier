package ASM.node;

import java.util.ArrayList;

import ASM.ASMVisitor;
import ASM.node.ins.ASMIns;

public class ASMBlock extends ASMNode {
    private String Label;
    ArrayList<ASMIns> insList;
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Label + ":\n");
        for (ASMIns ins : insList) {
            sb.append("    " + ins.toString() + "\n");
        }
        return sb.toString();
    }

    public ASMBlock(String Label) {
        this.Label = Label;
        insList = new ArrayList<ASMIns>();
    }

    @Override
    public <T> T accept(ASMVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
