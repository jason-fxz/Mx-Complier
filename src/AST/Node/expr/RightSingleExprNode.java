package AST.Node.expr;

import AST.ASTVisitor;
import Util.position;

public class RightSingleExprNode extends ExprNode {
    public ExprNode lhs;

    public enum unaryrightOpType {
        inc("++"),
        dec("--");

        private final String symbol;

        unaryrightOpType(String symbol) {
            this.symbol = symbol;
        }

        public static unaryrightOpType fromString(String symbol) {
            for (unaryrightOpType op : unaryrightOpType.values()) {
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

    public unaryrightOpType op;
    

    public RightSingleExprNode(position pos, ExprNode left, unaryrightOpType op) {
        super(pos);
        this.lhs = left;
        this.op = op;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return lhs.toString() + op.toString();
    }

}
