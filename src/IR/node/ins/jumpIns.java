package IR.node.ins;

public class jumpIns extends IRIns {
    String label;

    public jumpIns(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "br label %" + label;
    }
    
}
