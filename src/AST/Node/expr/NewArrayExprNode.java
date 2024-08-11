package AST.Node.expr;

import java.util.ArrayList;

import AST.ASTVisitor;
import Util.position;

public class NewArrayExprNode extends ExprNode {
    public String name;
    public int dim = 0;
    public ArrayInitNode array = null;
    public ArrayList<ExprNode> dimsize = new ArrayList<>();

    public NewArrayExprNode(position pos, String name, int dim) {
        super(pos);
        this.name = name;
        this.dim = dim;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("new " + name);
        for (int i = 0; i < dimsize.size(); ++i) {
            if (dimsize.get(i) == null) {
                str.append("[]");
            } else {
                str.append("[" + dimsize.get(i).toString() + "]");
            }
        }
        if (array != null) {
            str.append(array.toString());
        }
        return str.toString();
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
    
}
