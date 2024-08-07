package Util.error;

import Util.position;


public class InvalidControlFlowError extends error {
    public InvalidControlFlowError(String message, position pos) {
        super("Invalid Identifier Error: " + message, pos);
    }
}