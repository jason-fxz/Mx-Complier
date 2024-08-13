package Util.error;

import Util.position;


public class TypeMismatchError extends SemanticError {
    public TypeMismatchError(String message, position pos) {
        super("Type Mismatch Error: " + message, "Type Mismatch", pos);
    }
}