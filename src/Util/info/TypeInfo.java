package Util.info;

import Grammar.MxParser.TypeNameContext;

public class TypeInfo extends BaseInfo {
    public String typeName;
    public int dim;
    public boolean isBasic;
    public boolean isFunc = false;

    public String GetTypeName() {
        return typeName;
    }

    public TypeInfo(String typeName) {
        this.typeName = typeName;
        this.isBasic = (typeName == "int" || typeName == "bool" || typeName == "string" || typeName == "void");
    }

    public TypeInfo(TypeNameContext ctx) {
        this.typeName = ctx.type().getText();
        this.dim = ctx.arrayUnit().size();
        this.isBasic = (typeName == "int" || typeName == "bool" || typeName == "string" || typeName == "void");
    }

    
}
