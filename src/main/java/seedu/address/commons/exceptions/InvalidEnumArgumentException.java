package seedu.address.commons.exceptions;

/**
 * Signals that an enum value has not been accounted for in a switch case.
 */
public class InvalidEnumArgumentException extends Exception {
    /**
     * @param message should contain relevant information on the invalid enum
     */
    public InvalidEnumArgumentException(String message) {
        super(message);
    }
}
