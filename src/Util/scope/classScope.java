package Util.scope;

import Util.info.ClassInfo;

public class classScope extends Scope {
    public ClassInfo thisclass = null;
    public classScope(Scope parScope) {
        super(parScope, ScopeType.classScope);
    }
    
}
