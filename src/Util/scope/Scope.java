package Util.scope;

import java.util.HashMap;

import Util.IRLabeler;
import Util.position;
import Util.error.MultipleDefinitionsError;
import Util.info.FuncInfo;
import Util.info.TypeInfo;

public class Scope {
    protected HashMap<String, TypeInfo> member;
    protected HashMap<String, String> varLabel;

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
        varLabel = new HashMap<>();
        this.parScope = parScope;
        this.type = ScopeType.blockScope;
    }

    public Scope(Scope parScope, ScopeType type) {
        member = new HashMap<>();
        varLabel = new HashMap<>();
        this.parScope = parScope;
        this.type = type;
    }

    public Scope parScope() {
        return parScope;
    }

    public void DefVar(String name, TypeInfo type, position pos) {
        if (member.containsKey(name)) {
            throw new MultipleDefinitionsError(name, pos);
        }
        if (this.type.equals(ScopeType.globalScope)) { // global var
            varLabel.put(name, "@" + name);
        } else {
            if (this.type.equals(ScopeType.classScope)) {
                var classScope = (classScope) this;
                // class var
                // Use of class member !!!
                varLabel.put(name, "%this::" + name);
            } else {
                // local var
                varLabel.put(name, IRLabeler.getIdLabel("%" + name));
            }
        }
        member.put(name, type);
    }

    public String getVarLabel(String name) {
        if (varLabel.containsKey(name)) return varLabel.get(name);
        else if (parScope != null) return parScope.getVarLabel(name);
        else throw new RuntimeException("No such var in scope");
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
