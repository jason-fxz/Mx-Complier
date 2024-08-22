package IR.node.def;

import IR.IRvisitor;
import IR.type.IRStructType;

public class IRStructDef extends IRDefNode {
    public IRStructType structType;

    public IRStructDef(IRStructType structType) {
        this.structType = structType;
    }

    @Override
    public String toString() {
        return "%" + structType.gettypeName() + " = type " + structType.toString();
    }

    @Override
    public <T> T accecpt(IRvisitor<T> visitor) {
        return visitor.visit(this);
    }
    

}
