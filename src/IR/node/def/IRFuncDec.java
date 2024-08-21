package IR.node.def;

import java.util.ArrayList;

import IR.IRvisitor;
import IR.type.IRType;

public class IRFuncDec extends IRDefNode {
    String name;
    IRType returnType;
    ArrayList<IRType> params;

    public IRFuncDec(String name, IRType returnType, ArrayList<IRType> params) {
        this.name = name;
        this.returnType = returnType;
        this.params = params;
    }

    public IRFuncDec(String name, IRType returnType, IRType... params) {
        this.name = name;
        this.returnType = returnType;
        this.params = new ArrayList<>();
        for (IRType param : params) {
            this.params.add(param);
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("declare " + returnType.toString() + " " + name + "(");
        for (int i = 0; i < params.size(); i++) {
            if (i != 0) {
                str.append(", ");
            }
            str.append(params.get(i).toString());
        }
        str.append(")\n");
        return str.toString();
    }

    @Override
    public <T> T accecpt(IRvisitor<T> visitor) {
        return visitor.visit(this);
    }
}
