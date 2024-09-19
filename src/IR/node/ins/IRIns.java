package IR.node.ins;

import IR.IRvisitor;
import IR.item.IRitem;
import IR.node.IRNode;

import java.util.Map;

public abstract class IRIns extends IRNode {
    public boolean removed = false;
    
    public abstract void replaceUse(IRitem old, IRitem nw);

    public abstract void replaceUse(Map<IRitem, IRitem> map);

    @Override
    public abstract String toString();

    @Override
    public abstract <T> T accecpt(IRvisitor<T> visitor);
}