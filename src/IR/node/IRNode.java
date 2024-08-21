package IR.node;

import IR.IRvisitor;

public abstract class IRNode {
    public abstract String toString();

    public abstract <T> T accecpt(IRvisitor<T> visitor);
    
}
