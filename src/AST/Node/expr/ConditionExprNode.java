package AST.Node.expr;

import AST.ASTVisitor;
import Util.position;

public class ConditionExprNode extends ExprNode {
    public ExprNode cond;
    public ExprNode thenExpr;
    public ExprNode elseExpr;

    public ConditionExprNode(position pos, ExprNode cond, ExprNode thenExpr, ExprNode elseExpr) {
        super(pos);
        this.cond = cond;
        this.thenExpr = thenExpr;
        this.elseExpr = elseExpr;
    }

    @Override
    public String toString() {
        return "(" + cond.toString() + " ? " + thenExpr.toString() + " : " + elseExpr.toString() + ")";
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}


