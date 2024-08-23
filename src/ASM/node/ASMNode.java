package ASM.node;

import ASM.ASMVisitor;

public abstract class ASMNode {
    public String comment;
    
    public ASMNode Note(String comment) {
        this.comment = comment;
        return this;
    }

    public String addComment(String s) {
        if (comment != null) {
            return String.format("%-40s  # %s\n", s, comment);
        } else {
            return s + "\n";
        }
    }

    public abstract String toString();

    public abstract <T> T accept(ASMVisitor<T> visitor);
}
