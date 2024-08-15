package IR.type;

import Util.BuiltinElements;
import Util.info.TypeInfo;

public class IRType {
    static public IRType IRvoidType = new IRType("void");
    static public IRType IRBoolType = new IRType("i1");
    static public IRType IRIntType = new IRType("i32");
    static public IRType IRPtrType = new IRType("ptr");

    private String typeName;
    private String typeLabel;

    public IRType(String typeName) {
        this.typeName = typeName;
    }

    public IRType(TypeInfo type) {
        this.typeLabel = type.GetTypeName();
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

    public String gettypeName() {
        return typeName;
    }

    public String getTypeLabel() {
        return typeLabel;
    }
}