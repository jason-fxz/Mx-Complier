package Util.info;

import Grammar.MxParser.TypeNameContext;

public class TypeInfo extends BaseInfo {
    public String typeName;
    public int dim;
    public boolean isBasic;
    public boolean isFunc = false;

    public String GetTypeName() {
        return typeName + "[]".repeat(dim);
    }

    public TypeInfo(String typeName) {
        this.typeName = typeName;
        this.isBasic = (typeName == "int" || typeName == "bool" || typeName == "string" || typeName == "void");
    }

    public TypeInfo(String typeName, int dim) {
        this.dim  = dim;
        this.typeName = typeName;
        this.isBasic = (typeName == "int" || typeName == "bool" || typeName == "string" || typeName == "void");
    }

    public TypeInfo(TypeNameContext ctx) {
        this.typeName = ctx.type().getText();
        this.dim = ctx.LeftBrack().size();
        this.isBasic = (typeName == "int" || typeName == "bool" || typeName == "string" || typeName == "void");
    }

    boolean equals(TypeInfo other) {
        return typeName.equals(other.typeName) && dim == other.dim;
    }
}
