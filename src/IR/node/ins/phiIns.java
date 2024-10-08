package IR.node.ins;

import IR.IRvisitor;
import IR.item.IRLiteral;
import IR.item.IRitem;
import IR.item.IRvar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class phiIns extends IRIns {
    public IRvar result;
    static public class phiItem {
        public IRitem value;
        public String label;
        public phiItem(IRitem value, String label) {
            this.value = value;
            this.label = label;
        }
    }
    public ArrayList<phiItem> values;
    public IRvar var; // Used in Mem2Reg

    public phiIns(IRvar result, ArrayList<phiItem> values) {
        this.result = result;
        this.values = values;
    }

    public phiIns(IRvar result, phiItem ...items) {
        this.result = result;
        this.values = new ArrayList<>();
        for (var it : items) {
            values.add(it);
        }
    }

    public void addValue(IRitem value, String label) {
        values.add(new phiItem(value, label));
    }

    public IRitem getValue(String label) {
        for (var val : values) {
            if (val.label.equals(label)) {
                return val.value;
            }
        }
        throw new RuntimeException("phiIns: getValue: No such label in phiIns `" + label);
    }

    public void replaceLabel(String old, String nw) {
        for (var val : values) {
            if (val.label.equals(old)) {
                val.label = nw;
                break;
            }
        }
    }

    public void removeLabel(String label) {
        values.removeIf(val -> val.label.equals(label));
    }

    @Override
    public String toString() {
        String str = result.toString() + " = phi " + result.type.toString() + " ";
        for (var val : values) {
            if (val.value == null) {
                IRLiteral emptyvar = new IRLiteral(result.type);
                str += "[ " + emptyvar.toString() + ", %" + val.label + " ], ";
            } else {
                str += "[ " + val.value.toString() + ", %" + val.label + " ], ";
            }
        }
        if (str.endsWith(", ")) {
            str = str.substring(0, str.length() - 2);
        }
        return str;
    }

    @Override
    public <T> T accecpt(IRvisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public void replaceUse(IRitem old, IRitem nw) {
        for (var val : values) {
            if (val.value != null && val.value.equals(old)) {
                val.value = nw;
            }
        }
    }

    @Override
    public void replaceUse(Map<IRitem, IRitem> map) {
        for (var val : values) {
            if (map.containsKey(val.value)) {
                val.value = map.get(val.value);
            }
        }
    }

    @Override
    public Set<IRvar> getUses() {
        Set<IRvar> res = new HashSet<>();
        for (var val : values) {
            if (val.value instanceof IRvar && ((IRvar) val.value).isLocal()) {
                res.add((IRvar) val.value);
            }
        }
        return res;
    }

    @Override
    public IRvar getDef() {
        return result;
    }
}

