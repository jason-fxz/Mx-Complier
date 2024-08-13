package Util.error;

import Util.position;


public class InvalidTypeError extends SemanticError {
    public InvalidTypeError(String message, position pos) {
        super("Invalid Type Error: " + message, "Invalid Type", pos);
    }
}