package IR.item;

import IR.type.IRType;

public class IRLiteral extends IRitem {
    public String value;

    public IRLiteral(IRType type, String value) {
        super(type);
        this.value = value;
    }

    public IRLiteral(String value) {
        super();
        this.value = value;
        if (value.equals("true") || value.equals("false")) {
            this.type = IRType.IRBoolType;
        } else if (value.equals("null")) {
            this.type = IRType.IRPtrType;
        } else if (value.equals("void")) {
            this.type = IRType.IRvoidType;
        } else {
            this.type = IRType.IRIntType;
        }
    }

    public int getInt() {
        if (value.equals("true")) {
            return 1;
        } else if (value.equals("false")) {
            return 0;
        } else if (value.equals("null")) {
            return 0;
        } else if (value.equals("void")) {
            return 0;
        } else return Integer.parseInt(value);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IRLiteral) {
            return value.equals(((IRLiteral) obj).value);
        }
        return false;
    }
}
