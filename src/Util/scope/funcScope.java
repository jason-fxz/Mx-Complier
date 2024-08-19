package Util.scope;

import Util.position;
import Util.error.MultipleDefinitionsError;
import Util.info.TypeInfo;

public class funcScope extends Scope {
    public TypeInfo retType = null;
    public boolean haveRet = false;
    public funcScope(Scope parScope) {
        super(parScope, ScopeType.funcScope);
    }


    public void DefParamVar(String name, TypeInfo type, position pos)  {
        if (member.containsKey(name)) {
            throw new MultipleDefinitionsError(name, pos);
        }
        varLabel.put(name, "%" + name + ".addr");
        member.put(name, type);
    }
    
}
