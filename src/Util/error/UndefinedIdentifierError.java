package Util.error;

import Util.position;

public class UndefinedIdentifierError extends SemanticError {
    public UndefinedIdentifierError(String message, position pos) {
        super("Undefined Identifier Error: " + message, "Undefined Identifier", pos);
    }
}
