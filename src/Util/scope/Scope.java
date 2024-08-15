package Util.scope;

import java.util.HashMap;

import Util.IRLabeler;
import Util.position;
import Util.error.MultipleDefinitionsError;
import Util.info.FuncInfo;
import Util.info.TypeInfo;

public class Scope {
    private HashMap<String, TypeInfo> member;
    private HashMap<String, String> varLabel;

    protected Scope parScope;
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
        if (this.type.equals(ScopeType.globalScope)) {
            varLabel.put(name, "@" + name);
        } else {
            if (this.type.equals(ScopeType.classScope)) {
                // var classScope = (classScope)this;
                // varLabel.put(name, "%" + classScope.className + "." + name);
            } else {
                varLabel.put(name, "%" + name);
            }
        }
    }

    public String getVarLabel(String name) {
        return varLabel.get(name);
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

    public FuncInfo GetFuncInfo(String name) {
        if (parScope != null) return parScope.GetFuncInfo(name);
        return null;
    }
}
