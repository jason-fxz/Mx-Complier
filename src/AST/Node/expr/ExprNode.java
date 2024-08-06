package AST.Node.expr;

import AST.Node.ASTNode;
import Util.position;


public abstract class ExprNode extends ASTNode {
    
    public ExprNode(position pos) {
        super(pos);
    }
}
