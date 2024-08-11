package Util.info;

public class ExprInfo extends TypeInfo {
    public boolean isLvalue;
    public ExprInfo(String typeName, boolean isLvalue) {
        super(typeName);
        this.isLvalue = isLvalue;
    }
    public boolean equals(TypeInfo other) {
        return typeName.equals(other.typeName) && dim == other.dim;
    }
    public boolean equals(ExprInfo other) {
        return typeName.equals(other.typeName) && dim == other.dim;
    }
    
    
}
