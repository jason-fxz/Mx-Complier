package Util.error;

import Util.position;


/*
 * Invalid Identifier
 * Multiple Definitions
 * Undefined ldentifier
 * Type Mismatch
 * Invalid Control Flow
 * Invalid Type
 * Missing ReturnStatement
 * Dimension Out Of Bound
 */
public abstract class error extends RuntimeException {
    private position pos;
    private String message;
    private String type;

    public String getErrorType() {
        return type;
    }

    public error(String message, String type, position pos) {
        this.type = type;
        this.message = message;
        this.pos = pos;
    }

    @Override
    public String toString() {
        return message + " at " + pos.toString();
    }

}
