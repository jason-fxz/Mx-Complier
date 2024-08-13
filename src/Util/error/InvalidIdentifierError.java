package Util.error;

import Util.position;


public class InvalidIdentifierError extends SemanticError {
    public InvalidIdentifierError(String message, position pos) {
        super("Invalid Identifier Error: " + message, "Invalid Identifier", pos);
    }
}