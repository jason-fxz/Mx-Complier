package AST.Node.expr;

import java.util.ArrayList;

import AST.ASTVisitor;
import Util.position;

public class FmtStringExprNode extends ExprNode {
    public ArrayList<String> strlist = new ArrayList<>();
    public ArrayList<ExprNode> exprlist = new ArrayList<>();

    public FmtStringExprNode(position pos) {
        super(pos);
    }

    @Override
    public String toString() {
        String str = "f\"";
        for (int i = 0; i < strlist.size(); i++) {
            if (i < exprlist.size()) {
                str += strlist.get(i).toString();
                str += exprlist.get(i).toString();
            } else {
                str += strlist.get(i).toString();
            }
        }
        str += "\"";
        return str;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }



}
