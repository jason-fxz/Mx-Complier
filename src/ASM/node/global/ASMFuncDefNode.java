package ASM.node.global;

import java.util.ArrayList;
import java.util.HashMap;

import ASM.ASMVisitor;
import ASM.node.ASMBlock;
import ASM.node.ASMNode;



public class ASMFuncDefNode extends ASMNode {
    public String name;
    public int paramCnt;
    public ArrayList<ASMBlock> blocks;
    public int stackSize = 0;
    public int stackVarAddrMax = 0;
    public HashMap<String, Integer> stackAddrBaseOns0;
    public HashMap<String, Integer> paramsId;
    // public boolean isGlobal = true;

    public int stackSpillOffset = 0; // sp + offset;  + is for spilled vars, - is for saved regs and call params
    public int regCallerSaveCur = 0;
    public int regCalleeSaveCur = 0;

    public ASMFuncDefNode(String name, int paramCnt) {
        this.name = name;
        this.paramCnt = paramCnt;
        blocks = new ArrayList<ASMBlock>();
        stackAddrBaseOns0 = new HashMap<>();
        stackSize = 8;
        paramsId = new HashMap<>();
        
    }

    public void addBlock(ASMBlock block) {
        blocks.add(block);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-38s   # -- Begin function %s\n", "    .globl " + name, name));

        for (int i = 0; i < blocks.size(); i++) {
            if (i == blocks.size() - 1) {
                sb.append(blocks.get(i).toString());
            } else {
                // sb.append(blocks.get(i).toString());

                sb.append(blocks.get(i).toString(blocks.get(i + 1).getLabel()));
            }
        }
        sb.append("                                          # -- End function " + name + "\n");
        return sb.toString();
        
    }
    @Override
    public <T> T accept(ASMVisitor<T> visitor) {
        return visitor.visit(this);
    }
    
    public String getName() {
        return name;
    }

    public int countBytes() {
        int cnt = 0;
        for (ASMBlock block : blocks) {
            cnt += block.countBytes();
        }
        return cnt;
    }
}
