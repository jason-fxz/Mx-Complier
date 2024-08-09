package AST.Node.expr;


import AST.ASTVisitor;
import Util.position;

public class NewVarExprNode extends ExprNode {
    public String name;

    public NewVarExprNode(position pos, String name) {
        super(pos);
        this.name = name;
    }

    @Override
    public String toString() {
        return "new " + name + "()";
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
    
}
