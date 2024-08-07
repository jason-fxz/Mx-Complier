package AST.Node.def;


import AST.ASTVisitor;
import AST.Node.ASTNode;
import AST.Node.expr.ExprNode;
import Util.position;
import Util.info.TypeInfo;

public class VarDefNode extends ASTNode{
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
        return type.GetName() + " " + name + " = " + init.toString();
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

}
