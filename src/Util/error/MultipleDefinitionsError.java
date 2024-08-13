package Util.error;

import Util.position;


public class MultipleDefinitionsError extends SemanticError {
    public MultipleDefinitionsError(String message, position pos) {
        super("Multiple Definitions Error: " + message, "Multiple Definitions", pos);
    }
}