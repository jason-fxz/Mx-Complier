package Util.error;

import Util.position;


public class InvalidControlFlowError extends SemanticError {
    public InvalidControlFlowError(String message, position pos) {
        super("Invalid Control Flow Error: " + message, "Invalid Control Flow", pos);
    }
}