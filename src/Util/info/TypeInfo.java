package Util.info;


public class TypeInfo {
    public String typeName;
    public int dim;
    public boolean isBasic;

    public String GetName() {
        return typeName;
    }

    public TypeInfo(String typeName) {
        this.typeName = typeName;
        this.isBasic = (typeName == "int" || typeName == "bool" || typeName == "string" || typeName == "void");
    }
}
