package IR.node.ins;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import IR.IRvisitor;
import IR.item.IRitem;
import IR.item.IRvar;

public class jumpIns extends IRIns {
    public String label;

    public jumpIns(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void replaceLabel(String old, String nw) {
        if (label.equals(old)) {
            label = nw;
        }
    }

    @Override
    public String toString() {
        return "br label %" + label;
    }

    @Override
    public <T> T accecpt(IRvisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public void replaceUse(IRitem old, IRitem nw) {
    }

    @Override
    public void replaceUse(Map<IRitem, IRitem> map) {
    }

    @Override
    public Set<IRvar> getUses() {
        return new HashSet<>();
    }

    @Override
    public IRvar getDef() {
        return null;
    }

}
