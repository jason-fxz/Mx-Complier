package Util.scope;

import java.util.HashMap;

import Util.error.MultipleDefinitionsError;
import Util.info.FuncInfo;
import Util.info.TypeInfo;

public class classScope extends Scope {
    public String className = null;
    private HashMap<String, FuncInfo> FunDefs;

    public void DefFunc(String name, FuncInfo func) {
        if (FunDefs.containsKey(name)) {
            throw new RuntimeException("FUCKCKCC");
            // throw new MultipleDefinitionsError(name, func.defpos);
        }
        TypeInfo type = new TypeInfo(func.retType);
        type.isFunc = true;
        FunDefs.put(name, func);
    }

    public classScope(Scope parScope) {
        super(parScope, ScopeType.classScope);
        FunDefs = new HashMap<>();
    }

    @Override
    public FuncInfo GetFuncInfo(String name) {
        if (FunDefs.containsKey(name)) {
            return FunDefs.get(name);
        }
        if (parScope != null) return parScope.GetFuncInfo(name);
        return null;
    }
    
}
