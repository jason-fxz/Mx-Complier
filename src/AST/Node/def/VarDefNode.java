package AST.Node.def;

import AST.ASTVisitor;
import AST.Node.expr.ExprNode;
import Util.position;
import Util.info.TypeInfo;

public class VarDefNode extends DefNode {
    public String name;
    public TypeInfo type;
    public ExprNode init;

    public VarDefNode(position pos, String name, TypeInfo type, ExprNode init) {
        super(pos);
        this.name = name;
        this.type = type;
        this.init = init;
    }

    @Override
    public String toString() {
        if (init != null)
            return type.GetTypeName() + " " + name + " = " + init.toString();
        else return type.GetTypeName() + " " + name;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
