package IR.node.def;

import IR.IRvisitor;
import IR.node.IRNode;

public abstract class IRDefNode extends IRNode {
    @Override
    public abstract String toString();

    @Override
    public abstract <T> T accecpt(IRvisitor<T> visitor);
}
