package AST.Node.expr;

import AST.ASTVisitor;
import Util.position;

public class BinaryExprNode extends ExprNode {
    public ExprNode lhs;
    public ExprNode rhs;
    public enum binaryOpType {
        mul("*"),
        div("/"),
        mod("%"),
        add("+"),
        sub("-"),
        shl("<<"),
        shr(">>"),
        lt("<"),
        gt(">"),
        le("<="),
        ge(">="),
        eq("=="),
        ne("!="),
        and("&&"),
        or("||"),
        bitand("&"),
        bitor("|"),
        bitxor("^");

        private final String symbol;

        binaryOpType(String symbol) {
            this.symbol = symbol;
        }

        public static binaryOpType fromString(String symbol) {
            for (binaryOpType op : binaryOpType.values()) {
                if (op.symbol.equals(symbol)) {
                    return op;
                }
            }
            throw new RuntimeException("Invalid binary operator: " + symbol);
        }

        @Override
        public String toString() {
            return symbol;
        }

        public String toIRIns() {
            switch (this) {
                case mul: return "mul";
                case add: return "add";
                case sub: return "sub";
                case div: return "sdiv";
                case mod: return "srem";
                case shl: return "shl";
                case shr: return "ashr";
                case and: return "and";
                case or: return "or";
                case bitand: return "and";
                case bitor: return "or";
                case bitxor: return "xor";
                case lt: return "slt";
                case gt: return "sgt";
                case le: return "sle";
                case ge: return "sge";
                case eq: return "eq";
                case ne: return "ne";
                default:
                    throw new RuntimeException("toIRIns(): Invalid binary operator: " + this);
            }
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
    }

    public binaryOpType op;

    public BinaryExprNode(position pos, ExprNode left, ExprNode right, binaryOpType op) {
        super(pos);
        this.lhs = left;
        this.rhs = right;
        this.op = op;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return lhs.toString() + " " + op.toString() + " " + rhs.toString();
    }
    
}
