package IR.node;

import java.util.ArrayList;
import java.util.HashSet;

import IR.IRvisitor;
import IR.item.IRvar;
import IR.node.ins.*;

public class IRblock extends IRNode {
    public String Label;
    public ArrayList<phiIns> phiList = new ArrayList<>();
    public ArrayList<IRIns> insList;
    public IRIns endIns;
    public int index;
    
    public int loopDepth = 0;

    private HashSet<IRblock> prevBlocks;
    private HashSet<IRblock> nextBlocks;

    public void initPrevNextBlocks() {
        prevBlocks = new HashSet<>();
        nextBlocks = new HashSet<>();
    }

    public HashSet<IRblock> getPrevBlocks() {
        return prevBlocks;
    }

    public HashSet<IRblock> getNextBlocks() {
        return nextBlocks;
    }

    public HashSet<IRvar> getLiveIn() {
        if (phiList.size() != 0) {
            return phiList.get(0).liveIn;
        } else if (insList.size() != 0) {
            return insList.get(0).liveIn;
        } else {
            return endIns.liveIn;
        }
    }

    public HashSet<IRvar> getLiveOut() {
        return endIns.liveOut;
    }

    public void addPrevBlock(IRblock block) {
        prevBlocks.add(block);
    }

    public void addNextBlock(IRblock block) {
        nextBlocks.add(block);
    }

    
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
            return ;
            // throw new RuntimeException("endIns have been set");
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
        if (ins instanceof returnIns || ins instanceof branchIns || ins instanceof jumpIns || ins instanceof icmpbranchIns) {
            endIns = ins;
        } else {
            throw new RuntimeException("endIns should be the Jump instruction in a block");
        }
        // Add next blocks
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
        str.append(String.format("%-80s", Label + ":"));
        // str.append(Label + ":\n");
        str.append(";  ");
        if (loopDepth != 0) {
            str.append("loopDepth: " + loopDepth + ";  ");
        }
        if (prevBlocks != null) {
            str.append("prev: ");
            for (var block : prevBlocks) {
                str.append(block.Label + ", ");
            }
        }
        str.append("\n");

        for (phiIns phi : phiList) {
            str.append("  " + phi.toString() + "\n");
        }
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
    
    // public int StackVarCount() {
    //     int count = 0;
    //     for (var ins : insList) {
    //         if (ins instanceof allocaIns) count += 2;
    //         else if (ins instanceof arithIns) count += 1;
    //         else if (ins instanceof callIns && ((callIns)ins).result != null) count += 1; 
    //         else if (ins instanceof getelementptrIns) count += 1;
    //         else if (ins instanceof icmpIns) count += 1;
    //         else if (ins instanceof loadIns) count += 1;
    //         else if (ins instanceof phiIns) count += 1;
    //         else if (ins instanceof selectIns) count += 1;
    //     }
    //     return count;
    // }
    
    // public int maxCallparams() {
    //     int res = 0;
    //     for (var ins : insList) {
    //         if (ins instanceof callIns) res = Math.max(res, ((callIns)ins).args.size());
    //     }
    //     return res;
    // }
}
