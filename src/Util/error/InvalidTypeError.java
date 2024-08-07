package Util.error;

import Util.position;


public class InvalidTypeError extends error {
    public InvalidTypeError(String message, position pos) {
        super("Invalid Identifier Error: " + message, pos);
    }
}