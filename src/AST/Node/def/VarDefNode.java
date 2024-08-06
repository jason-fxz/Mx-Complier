package AST.Node.def;


import AST.ASTVisitor;
import AST.Node.ASTNode;
import AST.Node.expr.ExprNode;
import Util.Type;
import Util.position;

public class VarDefNode extends ASTNode{
    public String name;
    public Type type;
    public ExprNode init;
    
    public VarDefNode(position pos, String name, Type type, ExprNode init) {
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
