package AST.Node.expr;

import AST.Node.ASTNode;
import Util.position;
import Util.info.ExprInfo;

public abstract class ExprNode extends ASTNode {
    ExprInfo info;
    public ExprNode(position pos) {
        super(pos);
    }
}
