package AST.Node.expr;

import java.util.ArrayList;

import AST.ASTVisitor;
import Util.position;

public class ArrayInitNode extends ExprNode {
    public ArrayList<ExprNode> exprs = new ArrayList<>();
    public int dep;

    public ArrayInitNode(position pos) {
        super(pos);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("{");
        for (ExprNode expr : exprs) {
            str.append(expr.toString());
            str.append(", ");
        }
        if (exprs.size() > 0) {
            str.delete(str.length() - 2, str.length());
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