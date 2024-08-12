package Util.scope;

import Util.info.TypeInfo;

public class funcScope extends Scope {
    public TypeInfo retType = null;
    public boolean haveRet = false;
    public funcScope(Scope parScope) {
        super(parScope, ScopeType.funcScope);
    }
    
}
