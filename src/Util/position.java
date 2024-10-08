package Util;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;

public class position {
    static public String filename = "";
    private int row, col;

    public position() {
        this.row = 0;
        this.col = 0;
    }
    
    public position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public position(Token token) {
        this.row = token.getLine();
        this.col = token.getCharPositionInLine();
    }

    public position(TerminalNode terminal) {
        this(terminal.getSymbol());
    }

    public position(ParserRuleContext ctx) {
        this(ctx.getStart());
    }

    public int row() { return row; }

    public int col() {
        return col;
    }

    public String toString() { return filename + ":" + row + ":" + col; }
}
