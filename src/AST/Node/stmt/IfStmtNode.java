package AST.Node.stmt;


import AST.ASTVisitor;
import AST.Node.expr.ExprNode;
import Util.position;

public class IfStmtNode extends StmtNode {
    final public ExprNode condition;
    final public StmtNode thenStmt, elseStmt;

    public IfStmtNode(position pos, ExprNode cond, StmtNode thenStmt, StmtNode elseStmt) {
        super(pos);
        this.thenStmt = thenStmt;
        this.elseStmt = elseStmt;
        this.condition = cond;
    }

    @Override
    public String toString() {
        String str = "if ( " + condition.toString() + ")";
        if (thenStmt instanceof BlockStmtNode) {
            str += " " + thenStmt.toString();
        } else {
            indentDepth++;
            str += "\n" + thenStmt.toString();
            indentDepth--;
        }
        if (elseStmt != null) {
            if (str.endsWith("}")) {
                str += " else";
            } else {
                str += "\n" + super.toString() + "else";
            }
            if (elseStmt instanceof BlockStmtNode) {
                str += " " + elseStmt.toString();
            } else if (elseStmt instanceof IfStmtNode) {
                String elseStr = elseStmt.toString();
                str += " " + elseStr.substring(indentDepth * 2);
            } else {
                indentDepth++;
                str += "\n" + elseStmt.toString();
                indentDepth--;
            }
        }
        return super.toString() + str;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
