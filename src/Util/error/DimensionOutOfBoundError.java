package Util.error;

import Util.position;


public class DimensionOutOfBoundError extends error {
    public DimensionOutOfBoundError(String message, position pos) {
        super("Invalid Identifier Error: " + message, pos);
    }
}