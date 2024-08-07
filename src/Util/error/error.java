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

    public error(String message, position pos) {
        this.message = message;
        this.pos = pos;
    }

    public String toString() {
        return message + " at " + pos.toString();
    }

}
