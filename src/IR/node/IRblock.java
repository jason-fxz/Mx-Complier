package IR.node;

import java.util.ArrayList;

import IR.node.ins.IRIns;
import IR.node.ins.branchIns;
import IR.node.ins.jumpIns;
import IR.node.ins.returnIns;

public class IRblock {
    String Label;
    ArrayList<IRIns> insList;
    IRIns endIns;
    
    public IRblock(String Label) {
        this.Label = Label;
        insList = new ArrayList<IRIns>();
    }

    public void addIns(IRIns ins) {
        if (ins instanceof returnIns || ins instanceof branchIns || ins instanceof jumpIns) {
            throw new RuntimeException("endIns should be the last instruction in a block"); 
        }
        insList.add(ins);
    }

    public void setEndIns(IRIns ins) {
        if (ins instanceof returnIns || ins instanceof branchIns || ins instanceof jumpIns) {
            endIns = ins;
        } else {
            throw new RuntimeException("endIns should be the Jump instruction in a block");
        }
    }

    public String getLabel() {
        return Label;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(Label + ":\n");
        for (IRIns ins : insList) {
            str.append("  " + ins.toString());
        }
        str.append("  " + endIns.toString());
        return str.toString();
    }
    
}
