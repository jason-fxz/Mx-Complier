package IR.node.ins;

import IR.IRvisitor;
import IR.item.IRitem;
import IR.item.IRvar;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class getelementptrIns extends IRIns {
    public IRvar result;
    public IRitem pointer;
    public String type;
    public ArrayList<IRitem> indices;


    public getelementptrIns(IRvar result, IRitem pointer, String type, IRitem ... indices) {
        this.result = result;
        this.pointer = pointer;
        this.type = type;
        this.indices = new ArrayList<>();
        for (var i : indices) {
            this.indices.add(i);
        }
    }

    @Override
    public String toString() {
        String str = result.toString() + " = getelementptr " + type + ", ptr " + pointer.toString();
        for (var ind : indices) {
            str += ", " + ind.type.toString() + " " + ind.toString(); 
        }
        return str;
    }

    @Override
    public <T> T accecpt(IRvisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public void replaceUse(IRitem old, IRitem nw) {
        if (pointer.equals(old)) {
            pointer = nw;
        }
        for (int i = 0; i < indices.size(); i++) {
            if (indices.get(i).equals(old)) {
                indices.set(i, nw);
            }
        }
    }

    @Override
    public void replaceUse(Map<IRitem, IRitem> map) {
        if (map.containsKey(pointer)) {
            pointer = map.get(pointer);
        }
        for (int i = 0; i < indices.size(); i++) {
            if (map.containsKey(indices.get(i))) {
                indices.set(i, map.get(indices.get(i)));
            }
        }

    }

    @Override
    public List<IRvar> getUses() {
        List<IRvar> res = new ArrayList<>();
        if (pointer instanceof IRvar) {
            res.add((IRvar)pointer);
        }
        for (var ind : indices) {
            if (ind instanceof IRvar) {
                res.add((IRvar)ind);
            }
        }
        return res;
    }

    @Override
    public IRvar getDef() {
        return result;
    }
}