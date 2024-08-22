package IR.node.ins;

import IR.IRvisitor;

public class jumpIns extends IRIns {
    public String label;

    public jumpIns(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return "br label %" + label;
    }

    @Override
    public <T> T accecpt(IRvisitor<T> visitor) {
        return visitor.visit(this);
    }
    
}
