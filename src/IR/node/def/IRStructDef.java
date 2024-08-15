package IR.node.def;

import IR.type.IRStructType;

public class IRStructDef {
    IRStructType structType;

    public IRStructDef(IRStructType structType) {
        this.structType = structType;
    }

    @Override
    public String toString() {
        return structType.gettypeName() + " = type " + structType.toString();
    }
    
}
