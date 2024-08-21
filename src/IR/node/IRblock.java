package IR.node;

import java.util.ArrayList;

import IR.IRvisitor;
import IR.node.ins.IRIns;
import IR.node.ins.branchIns;
import IR.node.ins.jumpIns;
import IR.node.ins.returnIns;

public class IRblock extends IRNode {
    String Label;
    ArrayList<IRIns> insList;
    IRIns endIns;
    
    public IRblock(String Label) {
        this.Label = Label;
        insList = new ArrayList<IRIns>();
        endIns = null;
    }

    public void addIns(IRIns ins) {
        if (ins instanceof returnIns || ins instanceof branchIns || ins instanceof jumpIns) {
            throw new RuntimeException("endIns should be the last instruction in a block"); 
        }
        insList.add(ins);
    }

    public boolean isEnd() {
        return endIns != null;
    }

    public void setEndIns(IRIns ins) {
        if (endIns != null) {
            throw new RuntimeException("endIns have been set");
        }
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
        if (endIns == null && insList.size() == 0) {
            return "";
        }

        StringBuilder str = new StringBuilder();
        str.append(Label + ":\n");
        for (IRIns ins : insList) {
            str.append("  " + ins.toString() + "\n");
        }
        str.append("  " + endIns.toString() + "\n");
        return str.toString();
    }

    @Override
    public <T> T accecpt(IRvisitor<T> visitor) {
        return visitor.visit(this);
    }
    
    
}
