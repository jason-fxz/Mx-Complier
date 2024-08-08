package Util.error;

import Util.position;


public class TypeMismatchError extends SemanticError {
    public TypeMismatchError(String message, position pos) {
        super("Invalid Identifier Error: " + message, pos);
    }
}