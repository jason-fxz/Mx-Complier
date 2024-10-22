package IR.node.def;

import IR.IRvisitor;
import IR.item.IRLiteral;
import IR.item.IRvar;
import IR.type.IRType;

public class IRglobalVarDef extends IRDefNode {
    public IRvar var;
    public IRLiteral value;

    public IRglobalVarDef(IRvar var) {
        this.var = var;
        this.value = new IRLiteral(var.type);
    }

    public IRglobalVarDef(IRvar var, IRLiteral value) {
        this.var = var;
        this.value = value;
    }

    public String getVarName() {
        return var.toString().substring(1);
    }

    public String getTypeName() {
        return var.type.toString();
    }
    
    @Override
    public String toString() {
        String str = var.toString() + " = global " + var.type.toString() + " " + value.toString();
        if (var.type.equals(IRType.IRBoolType)) {
            // str += " false";
        } else if (var.type.equals(IRType.IRIntType)) {
            // str += " 0";
        } else if (var.type.equals(IRType.IRPtrType)) {
            // str += " null";
        } else {
            throw new RuntimeException("Unsupported type in global variable definition");
        }
        return str;
    }

    @Override
    public <T> T accecpt(IRvisitor<T> visitor) {
        return visitor.visit(this);
    }

    
}
