package IR.node.ins;

import IR.item.IRitem;
import IR.item.IRvar;
import java.util.ArrayList;

public class getelementptr extends IRIns {
    IRvar result;
    IRitem pointer;
    String type;
    ArrayList<Integer> indices;


    public getelementptr(IRvar result, IRitem pointer, String type, int ... indices) {
        this.result = result;
        this.pointer = pointer;
        this.type = type;
        this.indices = new ArrayList<>();
        for (int i : indices) {
            this.indices.add(i);
        }
    }

    @Override
    public String toString() {
        String str = result.toString() + " = getelementptr " + type + ", ptr " + pointer.toString();
        for (var ind : indices) {
            str += ", i32 " + ind; 
        }
        return str;
    }
}