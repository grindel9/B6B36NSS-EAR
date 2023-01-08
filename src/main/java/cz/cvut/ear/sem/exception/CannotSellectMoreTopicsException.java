package cz.cvut.ear.sem.exception;

/**
 * Indicates that student allready selected maxumum numbers of topics
 */
public class CannotSellectMoreTopicsException extends EarException {

    public CannotSellectMoreTopicsException(String message) {
        super(message);
    }
}
