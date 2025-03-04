package IR.node.ins;

import IR.IRvisitor;
import IR.item.IRitem;
import IR.item.IRvar;
import IR.node.IRNode;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class IRIns extends IRNode {
    public boolean removed = false;

    public HashSet<IRvar> liveIn;
    public HashSet<IRvar> liveOut;
    
    public abstract void replaceUse(IRitem old, IRitem nw);

    public abstract void replaceUse(Map<IRitem, IRitem> map);

    public abstract void replaceDef(IRvar old, IRvar nw);

    public abstract void replaceDef(Map<IRitem, IRitem> map);

    public abstract void replaceLabel(Map<String, String> map);

    public abstract Set<IRvar> getUses();

    public abstract IRvar getDef();

    @Override
    public abstract String toString();

    @Override
    public abstract <T> T accecpt(IRvisitor<T> visitor);

    @Override
    public abstract IRIns clone();
}