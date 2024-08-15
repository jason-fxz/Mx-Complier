package IR.item;

import IR.type.IRType;

abstract public class IRitem {
    public IRType type;

    public IRitem(IRType type) {
        this.type = type;
    }

    @Override
    public abstract String toString();


}
