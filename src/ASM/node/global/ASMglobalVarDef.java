package ASM.node.global;

import ASM.ASMVisitor;
import ASM.node.ASMNode;

public class ASMglobalVarDef extends ASMNode {
    private String name;
    private int size;

    @Override
    public String toString() {
        return name + ":\n    " + String.format("%-8s", ".zero") + size;
    }

    public ASMglobalVarDef(String name, int size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public <T> T accept(ASMVisitor<T> visitor) {
        return visitor.visit(this);
    }

    
}
