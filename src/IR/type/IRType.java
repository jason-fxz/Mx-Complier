package IR.type;

import Util.BuiltinElements;
import Util.info.TypeInfo;

public class IRType {
    private String typeName;
    public IRType(String typeName) {
        this.typeName = typeName;
    }

    public IRType(TypeInfo type) {
        if (type.isArray() || type.equals(BuiltinElements.stringType)) {
            this.typeName = "ptr";
        } else if (type.equals(BuiltinElements.intType)) {
            this.typeName = "i32";
        } else if (type.equals(BuiltinElements.boolType)) {
            this.typeName = "i1";
        } else if (type.equals(BuiltinElements.voidType)) {
            this.typeName = "void";
        } else if (type.isCustom()) {
            this.typeName = type.GetTypeName();
        } else {
            throw new RuntimeException("IRType Construct Unknown type: " + type);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IRType) return ((IRType) obj).typeName.equals(this.typeName);
        return false;
    }

    @Override
    public String toString() {
        return typeName;
    }
}