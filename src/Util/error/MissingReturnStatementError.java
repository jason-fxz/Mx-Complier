package Util.error;

import Util.position;


public class MissingReturnStatementError extends error {
    public MissingReturnStatementError(String message, position pos) {
        super("Multiple Definitions Error: " + message, pos);
    }
}