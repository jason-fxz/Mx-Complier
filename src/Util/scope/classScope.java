package Util.scope;


public class classScope extends Scope {
    public String className = null;
    public classScope(Scope parScope) {
        super(parScope, ScopeType.classScope);
    }
    
}
