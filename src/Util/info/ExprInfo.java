package Util.info;

public class ExprInfo extends TypeInfo {
    public boolean isLvalue;
    public FuncInfo funcinfo = null;
    public ExprInfo(String typeName, boolean isLvalue) {
        super(typeName);
        this.isLvalue = isLvalue;
    }
    public ExprInfo(String typeName, int dim, boolean isLvalue) {
        super(typeName, dim);
        this.isLvalue = isLvalue;
    }
    public boolean equals(TypeInfo other) {
        return (typeName.equals(other.typeName) && dim == other.dim) || (this.isNull() && other.acceptNull()) || (this.acceptNull() && other.isNull());
    }
    public boolean equals(ExprInfo other) {
        return (typeName.equals(other.typeName) && dim == other.dim) || (this.isNull() && other.acceptNull()) || (this.acceptNull() && other.isNull());
    }
    public ExprInfo(ExprInfo other) {
        super(other.typeName, other.dim);
        this.isLvalue = false;
    }
    public ExprInfo(TypeInfo other) {
        super(other.typeName, other.dim);
        this.isLvalue = false;
    }
    public ExprInfo(FuncInfo other) {
        super(other.retType);
        this.label = other.label;
        this.isFunc = true;
        this.isLvalue = false;
    }
}
