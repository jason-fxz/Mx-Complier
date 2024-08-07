package AST.Node.expr;

import java.util.ArrayList;

import AST.ASTVisitor;
import Util.position;

public class FmtStringExprNode extends ExprNode {
    ArrayList<StringExprNode> strlist = new ArrayList<>();
    ArrayList<ExprNode> exprlist = new ArrayList<>();


    public FmtStringExprNode(position pos) {
        super(pos);
    }

    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i < strlist.size(); i++) {
            if (i < exprlist.size()) {
                str += strlist.get(i).toString();
                str += exprlist.get(i).toString();
            } else {
                str += strlist.get(i).toString();
            }
        }
        return str;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }



}
