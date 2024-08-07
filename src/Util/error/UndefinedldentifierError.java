package Util.error;

import Util.position;

public class UndefinedldentifierError extends error {
    public UndefinedldentifierError(String message, position pos) {
        super("Undefined ldentifier Error: " + message, pos);
    }
}
