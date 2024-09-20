package IR.node.ins;

import IR.IRvisitor;
import IR.item.IRLiteral;
import IR.item.IRitem;
import IR.item.IRvar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


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
            if (val.value.equals(old)) {
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
    public List<IRvar> getUses() {
        List<IRvar> res = new ArrayList<>();
        for (var val : values) {
            if (val.value instanceof IRvar) {
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

