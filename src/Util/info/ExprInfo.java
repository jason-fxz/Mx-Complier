package Util.info;

public class ExprInfo extends TypeInfo {
    public boolean isLvalue;
    public ExprInfo(String typeName, boolean isLvalue) {
        super(typeName);
        this.isLvalue = isLvalue;
    }
    
}
