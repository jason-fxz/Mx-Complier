package ASM.node.global;

import ASM.ASMVisitor;
import ASM.node.ASMNode;

public class ASMglobalVarDef extends ASMNode {
    private String name;
    private int value;

    @Override
    public String toString() {
        return name + ":\n    " + String.format("%-8s", ".word") + value;
    }

    public ASMglobalVarDef(String name, int value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public <T> T accept(ASMVisitor<T> visitor) {
        return visitor.visit(this);
    }

    
}
