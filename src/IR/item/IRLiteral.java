package IR.item;

import IR.type.IRType;

public class IRLiteral extends IRitem {
    public String value;

    public IRLiteral(IRType type, String value) {
        super(type);
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
