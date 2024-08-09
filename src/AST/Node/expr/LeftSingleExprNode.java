package AST.Node.expr;

import AST.ASTVisitor;
import Util.position;

public class LeftSingleExprNode extends ExprNode {
    public ExprNode rhs;
    public enum unaryleftOpType {
        inc("++"),
        dec("--"),
        neg("-"), 
        not("!"),
        bitnot("~");

        private final String symbol;
        unaryleftOpType(String symbol) {
            this.symbol = symbol;
        }

        public static unaryleftOpType fromString(String symbol) {
            for (unaryleftOpType op : unaryleftOpType.values()) {
                if (op.symbol.equals(symbol)) {
                    return op;
                }
            }
            throw new RuntimeException("Invalid unaryleftOpType operator: " + symbol);
        }

        @Override
        public String toString() {
            return symbol;
        }

    };
    unaryleftOpType op;

    public LeftSingleExprNode(position pos, ExprNode right, unaryleftOpType op) {
        super(pos);
        this.rhs = right;
        this.op = op;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return op.toString() + rhs.toString();
    }
    
}
