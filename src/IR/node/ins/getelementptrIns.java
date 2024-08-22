package IR.node.ins;

import IR.IRvisitor;
import IR.item.IRitem;
import IR.item.IRvar;
import java.util.ArrayList;

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
}