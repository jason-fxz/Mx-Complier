package Util.error;

import Util.position;

public class SyntaxError extends error {
    public SyntaxError(String message, position pos) {
        super("Syntax Error: " + message, pos);
    }
}
