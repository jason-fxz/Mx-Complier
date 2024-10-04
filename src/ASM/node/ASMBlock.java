package ASM.node;

import java.util.ArrayList;

import ASM.ASMVisitor;
import ASM.node.ins.ASMIns;
import ASM.node.ins.ASMMoveIns;

public class ASMBlock extends ASMNode {
    private String Label;
    ArrayList<ASMIns> insList;
    ArrayList<ASMIns> jumpInsList;
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.addComment(Label + ":"));
        for (ASMIns ins : insList) {
            sb.append(ins.addComment("    " + ins.toString()));
        }
        for (ASMIns ins : jumpInsList) {
            sb.append(ins.addComment("    " + ins.toString()));
        }
        return sb.toString();
    }

    public ASMBlock(String Label) {
        this.Label = Label;
        insList = new ArrayList<ASMIns>();
        jumpInsList = new ArrayList<ASMIns>();
    }

    public void addIns(ASMIns ins) {
        if (ins instanceof ASMMoveIns) {
            ASMMoveIns moveIns = (ASMMoveIns) ins;
            if (moveIns.isUseless()) return;
        }
        insList.add(ins);
    }

    public void addIns(ASMIns ins, String comment) {
        ins.Note(comment);
        addIns(ins);
    }

    public void addJumpIns(ASMIns ins) {
        jumpInsList.add(ins);
    }

    public void addJumpIns(ASMIns ins, String comment) {
        ins.Note(comment);
        addJumpIns(ins);
    }

    @Override
    public <T> T accept(ASMVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
