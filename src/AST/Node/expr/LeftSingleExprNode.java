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

        public boolean equals(String symbol) {
            return this.symbol.equals(symbol);
        }

        public boolean in(String... symbols) {
            for (String s : symbols) {
                if (this.symbol.equals(s)) {
                    return true;
                }
            }
            return false;
        }

    };
    public unaryleftOpType op;

    public LeftSingleExprNode(position pos, ExprNode right, unaryleftOpType op) {
        super(pos);
        this.rhs = right;
        this.op = op;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return op.toString() + rhs.toString();
    }
    
}
