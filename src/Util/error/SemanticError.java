package Util.error;

import Util.position;

public class SemanticError extends error {
    public SemanticError(String message, position pos) {
        super("Semantic Error: " + message, "Semantic Error", pos);
    }
    public SemanticError(String message, String type, position pos) {
        super("Semantic Error: " + message, type, pos);
    }
}
