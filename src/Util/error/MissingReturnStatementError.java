package Util.error;

import Util.position;


public class MissingReturnStatementError extends SemanticError {
    public MissingReturnStatementError(String message, position pos) {
        super("Missing Return Statement Error: " + message, "Missing Return Statement", pos);
    }
}