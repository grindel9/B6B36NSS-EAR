package cz.cvut.ear.sem.exception;

/**
 * Signifies that invalid data have been provided to the application.
 */
public class ValidationException extends EarException {

    public ValidationException(String message) {
        super(message);
    }
}
