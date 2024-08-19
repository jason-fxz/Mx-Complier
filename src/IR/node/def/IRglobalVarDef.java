package IR.node.def;

import IR.item.IRvar;
import IR.type.IRType;

public class IRglobalVarDef {
    IRvar var;

    public IRglobalVarDef(IRvar var) {
        this.var = var;
    }
    
    @Override
    public String toString() {
        String str = var.toString() + " = global " + var.type.toString();
        if (var.type.equals(IRType.IRBoolType)) {
            str += " false";
        } else if (var.type.equals(IRType.IRIntType)) {
            str += " 0";
        } else if (var.type.equals(IRType.IRPtrType)) {
            str += " null";
        } else {
            throw new RuntimeException("Unsupported type in global variable definition");
        }
        return str;
    }
}
