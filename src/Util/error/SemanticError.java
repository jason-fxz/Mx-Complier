package Util.error;

import Util.position;

public class SemanticError extends error {
    public SemanticError(String message, position pos) {
        super("SemanticError: " + message, pos);
    }
}
