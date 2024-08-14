package IR.node;

import IR.IRvisitor;

public abstract class IRNode {
    public abstract String toString();

    abstract public <T> T accept(IRvisitor<T> visitor);
}
