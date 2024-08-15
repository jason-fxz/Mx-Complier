package IR.node.ins;

import IR.item.IRitem;
import IR.item.IRvar;

import java.util.ArrayList;



public class phiIns extends IRIns {
    IRvar result;
    public class phiItem {
        public IRitem value;
        public String label;
        public phiItem(IRvar value, String label) {
            this.value = value;
            this.label = label;
        }
    }
    ArrayList<phiItem> values;

    public phiIns(IRvar result, ArrayList<phiItem> values) {
        this.result = result;
        this.values = values;
    }

    @Override
    public String toString() {
        String str = result.toString() + " = phi " + result.type.toString() + " ";
        for (var val : values) {
            str += "[ " + val.value.toString() + ", %" + val.label + " ], ";
        }
        if (str.endsWith(", ")) {
            str = str.substring(0, str.length() - 2);
        }
        return str;
    }
}

