package cz.cvut.ear.sem.exception;

public class NotInRelatedTableCollection extends EarException {

    public NotInRelatedTableCollection(String message) {
        super(message);
    }

    public NotInRelatedTableCollection(String message, Throwable cause) {
        super(message, cause);
    }

    public static NotInRelatedTableCollection create(String resourceName, Object identifier, String entityName) {
        return new NotInRelatedTableCollection(resourceName + " identified by " + identifier + " in the collection in entity " + entityName);
    }
}
