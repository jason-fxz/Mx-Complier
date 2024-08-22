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
    public int stackVarAddrMax = 0; // above is used for function params
    public HashMap<String, Integer> stackAddrBaseOns0;

    public ASMFuncDefNode(String name, int paramCnt) {
        this.name = name;
        this.paramCnt = paramCnt;
        blocks = new ArrayList<ASMBlock>();
        stackAddrBaseOns0 = new HashMap<>();
        stackSize = 8;
    }

    public void addBlock(ASMBlock block) {
        blocks.add(block);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(".globl " + name + ":\n");
        for (ASMBlock block : blocks) {
            sb.append(block.toString());
        }
        return sb.toString();
        
    }
    @Override
    public <T> T accept(ASMVisitor<T> visitor) {
        return visitor.visit(this);
    }
    
    public String getName() {
        return name;
    }
}
