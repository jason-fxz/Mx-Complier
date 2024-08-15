package IR.item;

import IR.type.IRType;

public class IRvar extends IRitem{
    public String name;

    public IRvar(IRType type, String name) {
        super(type);
        this.name = name;
        if (!name.startsWith("@") && !name.startsWith("%")) {
            throw new RuntimeException("IRvar name should start with @ or %");
        }
    }

    @Override
    public String toString() {
        return name;
    }
    

}
