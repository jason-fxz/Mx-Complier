package Util.scope;

import java.util.HashMap;

import Util.position;
import Util.error.MultipleDefinitionsError;
import Util.info.TypeInfo;

public class Scope {
    private HashMap<String, TypeInfo> member;
    private Scope parScope;
    public enum ScopeType {
        globalScope, classScope, funcScope, loopScope, blockScope
    }
    ScopeType type;

    public Scope getLastloop() {
        Scope now = this;
        while (now != null && now.type != ScopeType.loopScope) {
            now = now.parScope;
        }
        return now;
    }

    public Scope getLastClass() {
        Scope now = this;
        while (now != null && now.type != ScopeType.classScope) {
            now = now.parScope;
        }
        return now;
    }

    public Scope getLastFunc() {
        Scope now = this;
        while (now != null && now.type != ScopeType.funcScope) {
            now = now.parScope;
        }
        return now;
    }

    public Scope(Scope parScope) {
        member = new HashMap<>();
        this.parScope = parScope; 
        this.type = ScopeType.blockScope;
    }

    public Scope(Scope parScope, ScopeType type) {
        member = new HashMap<>();
        this.parScope = parScope; 
        this.type = type;
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
