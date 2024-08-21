package ASM.node.global;

import java.util.ArrayList;

import ASM.ASMVisitor;
import ASM.node.ASMBlock;
import ASM.node.ASMNode;

public class ASMFuncDefNode extends ASMNode {
    private String name;
    private int paramCnt;
    private ArrayList<ASMBlock> blocks;

    public ASMFuncDefNode(String name, int paramCnt) {
        this.name = name;
        this.paramCnt = paramCnt;
        blocks = new ArrayList<ASMBlock>();
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
}
