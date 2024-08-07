package Util.error;

import Util.position;


public class InvalidIdentifierError extends error {
    public InvalidIdentifierError(String message, position pos) {
        super("Invalid Identifier Error: " + message, pos);
    }
}