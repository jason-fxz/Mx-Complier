package Util.error;

import Util.position;


public class TypeMismatchError extends error {
    public TypeMismatchError(String message, position pos) {
        super("Invalid Identifier Error: " + message, pos);
    }
}