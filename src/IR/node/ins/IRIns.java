package IR.node.ins;

import IR.IRvisitor;
import IR.node.IRNode;

public abstract class IRIns extends IRNode {
    @Override
    public abstract String toString();

    @Override
    public abstract <T> T accecpt(IRvisitor<T> visitor);
}