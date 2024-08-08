package Util.scope;

import java.util.HashMap;

import Util.position;
import Util.error.MultipleDefinitionsError;
import Util.info.TypeInfo;

public class Scope {
    private HashMap<String, TypeInfo> member;
    private Scope parScope;

    public Scope(Scope parScope) {
        member = new HashMap<>();
        this.parScope = parScope; 
    }

    public Scope parScope() {
        return parScope;
    }

    public void DefVar(String name, TypeInfo type, position pos)  {
        if (member.containsKey(name)) {
            throw new MultipleDefinitionsError(name, pos);
        }
        member.put(name, type);
    }

    public boolean haveVar(String name, boolean lookup) {
        if (member.containsKey(name)) return true;
        else if (parScope != null && lookup) return parScope.haveVar(name, true);
        else return false;
    }

    public TypeInfo getVarType(String name, boolean lookup) {
        if (member.containsKey(name)) return member.get(name);
        else if (parScope != null && lookup) return parScope.getVarType(name, true);
        return null;
    }
}
