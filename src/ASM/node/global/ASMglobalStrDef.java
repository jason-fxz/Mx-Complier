package ASM.node.global;

import ASM.ASMVisitor;
import ASM.node.ASMNode;

public class ASMglobalStrDef extends ASMNode {
    private String name;
    private String value;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name + ":\n    " + String.format("%-8s", ".asciz"));
        sb.append("\"");
        int len = 0;
        for (int i = 0; i < value.length(); ++i) {
            char c = value.charAt(i);
            if (i + 1 < value.length() && c == '\\') {
                char next = value.charAt(i + 1);
                if (next == 'n') {
                    sb.append("\\n");
                    ++i;
                } else if (next == '\"') {
                    sb.append("\\\"");
                    ++i;
                } else {
                    sb.append("\\\\");
                    ++i;
                }
            } else {
                sb.append(c);
            }
            ++len;
        }
        ++len;
        sb.append("\\000");
        sb.append("\"");
        return sb.toString();

    }

    public ASMglobalStrDef(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public <T> T accept(ASMVisitor<T> visitor) {
        return visitor.visit(this);
    }

    
}
