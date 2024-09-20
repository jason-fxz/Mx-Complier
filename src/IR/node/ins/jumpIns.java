package IR.node.ins;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public List<IRvar> getUses() {
        return new ArrayList<>();
    }

    @Override
    public IRvar getDef() {
        return null;
    }

}
