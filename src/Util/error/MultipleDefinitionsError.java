package Util.error;

import Util.position;


public class MultipleDefinitionsError extends error {
    public MultipleDefinitionsError(String message, position pos) {
        super("Multiple Definitions Error: " + message, pos);
    }
}