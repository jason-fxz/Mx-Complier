package IR.node.ins;

import IR.IRvisitor;
import IR.item.IRitem;
import IR.item.IRvar;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
    public void replaceDef(IRvar old, IRvar nw) {
        if (result.equals(old)) {
            result = nw;
        }
    }

    @Override
    public void replaceDef(Map<IRitem, IRitem> map) {
        if (map.containsKey(result)) {
            result = (IRvar)map.get(result);
        }
    }

    @Override
    public void replaceLabel(Map<String, String> map) {
        // No labels in getelementptrIns
    }

    @Override
    public Set<IRvar> getUses() {
        Set<IRvar> res = new HashSet<>();
        if (pointer instanceof IRvar && ((IRvar)pointer).isLocal()) {
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

    @Override
    public IRIns clone() {
        IRvar newResult = (IRvar) result.clone();
        IRitem newPointer = pointer.clone();
        ArrayList<IRitem> newIndices = new ArrayList<>();
        for (IRitem ind : indices) {
            newIndices.add(ind.clone());
        }
        
        getelementptrIns clone = new getelementptrIns(newResult, newPointer, type);
        clone.indices = newIndices;
        return clone;
    }
}