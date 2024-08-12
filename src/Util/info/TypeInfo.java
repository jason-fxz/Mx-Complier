package Util.info;


import Grammar.MxParser.TypeNameContext;

public class TypeInfo extends BaseInfo {
    public String typeName;
    public int dim = 0;
    public boolean isBasic = false;
    public boolean isVoid = false;
    public boolean isFunc = false;

    public String GetTypeName() {
        return typeName + "[]".repeat(dim);
    }

    public boolean isArray() {
        return dim > 0;
    }

    public boolean isNull() {
        return typeName.equals("null");
    }

    public boolean isCustom() {
        return !isBasic;
    }

    public boolean acceptNull() {
        return isCustom() || isArray();
    }


    public TypeInfo(String typeName) {
        this.typeName = typeName;
        this.isBasic = (typeName.equals("int") || typeName.equals("bool") || typeName.equals("string") || typeName.equals("void"));
        this.isVoid = (typeName.equals("void"));
        this.dim = 0;
    }

    public TypeInfo(String typeName, int dim) {
        this.dim  = dim;
        this.typeName = typeName;
        this.isBasic = (typeName.equals("int") || typeName.equals("bool") || typeName.equals("string") || typeName.equals("void"));
        this.isVoid = (typeName.equals("void"));
    }

    public TypeInfo(TypeNameContext ctx) {
        this.typeName = ctx.type().getText();
        this.dim = ctx.LeftBrack().size();
        this.isBasic = (typeName.equals("int") || typeName.equals("bool") || typeName.equals("string") || typeName.equals("void"));
        this.isVoid = (typeName.equals("void"));
    }

    public TypeInfo(TypeInfo other) {
        this.typeName = other.typeName;
        this.dim = other.dim;
        this.isBasic = other.isBasic;
        this.isVoid = other.isVoid;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof TypeInfo)) {
            throw new RuntimeException("bad equals");
        }
        var o = (TypeInfo) other;
        return typeName.equals(o.typeName) && dim == o.dim;
    }
}
