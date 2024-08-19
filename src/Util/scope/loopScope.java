package Util.scope;

import AST.Node.ASTNode;

public class loopScope extends Scope {
    ASTNode loopNode = null;
    public loopScope(Scope parScope) {
        super(parScope, ScopeType.loopScope);
    }
    
}

