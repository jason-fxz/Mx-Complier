package Util.error;

import Util.position;


public class DimensionOutOfBoundError extends SemanticError {
    public DimensionOutOfBoundError(String message, position pos) {
        super("Invalid Identifier Error: " + message, pos);
    }
}