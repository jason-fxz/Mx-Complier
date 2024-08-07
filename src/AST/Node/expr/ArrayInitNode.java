package AST.Node.expr;

import java.util.ArrayList;

import AST.ASTVisitor;
import Util.position;

public class ArrayInitNode extends ExprNode {
    ArrayList<ExprNode> exprs = new ArrayList<>();
    public int dep;
    
    public ArrayInitNode(position pos) {
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("{");
        for (ExprNode expr : exprs) {
            str.append(expr.toString());
            str.append(", ");
        }
        str.append("}");
        return str.toString();
    }

}



/*
 *  
 * 
 * 
 */