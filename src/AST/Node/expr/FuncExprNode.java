package AST.Node.expr;

import java.util.ArrayList;

import AST.ASTVisitor;
import Util.position;

public class FuncExprNode extends ExprNode {
    public ExprNode func;
    public ArrayList<ExprNode> args = new ArrayList<>();

    public FuncExprNode(position pos, ExprNode func) {
        super(pos);
        this.func = func;
    }

    @Override
    public String toString() {
        String str = "(";
        for (ExprNode arg : args) {
            str += arg.toString() + ", ";
        }
        str += ')';
        return str;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
    
}
