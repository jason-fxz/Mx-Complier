package AST.Node.expr;

import AST.ASTVisitor;
import Util.position;

public class MemberExprNode extends ExprNode {
    public ExprNode object;
    public String member;

    public MemberExprNode(position pos, ExprNode obj, String member) {
        super(pos);
        this.object = obj;
        this.member = member;
    }

    @Override
    public String toString() {
        return object.toString() + "." + member;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
    
}
