package Util;

import java.util.HashMap;

public class Type {
    public String typeName;
    public boolean isBasic;
    public HashMap<String, Type> member = null;
    
    public String GetName() {
        return typeName;
    }
}
