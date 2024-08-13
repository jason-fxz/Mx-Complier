package Util.error;

import Util.position;


public class DimensionOutOfBoundError extends SemanticError {
    public DimensionOutOfBoundError(String message, position pos) {
        super("Dimension Out Of Bound Error: " + message, "Dimension Out Of Bound",  pos);
    }
}