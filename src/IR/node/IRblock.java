package IR.node;

import java.util.ArrayList;

import IR.IRvisitor;
import IR.node.ins.*;

public class IRblock extends IRNode {
    public String Label;
    public ArrayList<IRIns> insList;
    public IRIns endIns;
    
    public IRblock(String Label) {
        this.Label = Label;
        insList = new ArrayList<IRIns>();
        endIns = null;
    }

    // public void unsafeaddIns(IRIns ins) {
    //     insList.add(ins);
    // }

    // public void ignaddIns(IRIns ins) {
    //     if (endIns != null) {
    //         return ;
    //     }
    //     if (ins instanceof returnIns || ins instanceof branchIns || ins instanceof jumpIns) {
    //         throw new RuntimeException("endIns should be the last instruction in a block"); 
    //     }
    //     insList.add(ins);
    // }

    public void addIns(IRIns ins) {
        if (endIns != null) {
            // return ;
            throw new RuntimeException("endIns have been set");
        }
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

    public boolean empty() {
        return insList.size() == 0 && endIns == null;
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
    
    public int StackVarCount() {
        int count = 0;
        for (var ins : insList) {
            if (ins instanceof allocaIns) count += 2;
            else if (ins instanceof arithIns) count += 1;
            else if (ins instanceof callIns && ((callIns)ins).result != null) count += 1; 
            else if (ins instanceof getelementptrIns) count += 1;
            else if (ins instanceof icmpIns) count += 1;
            else if (ins instanceof loadIns) count += 1;
            else if (ins instanceof phiIns) count += 1;
            else if (ins instanceof selectIns) count += 1;
        }
        return count;
    }
    
    public int maxCallparams() {
        int res = 0;
        for (var ins : insList) {
            if (ins instanceof callIns) res = Math.max(res, ((callIns)ins).args.size());
        }
        return res;
    }
}
