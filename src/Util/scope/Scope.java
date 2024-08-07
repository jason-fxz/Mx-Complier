package Util.scope;

import java.util.HashMap;

import Util.info.TypeInfo;

public class Scope {
    private HashMap<String, TypeInfo> ;
    private Scope parScope;

    public Scope(Scope parScope) {
        classMember = new HashMap<>();
        this.parScope = parScope; 
    }

    public Scope parScope() {
        return parScope;
    }



    
}
