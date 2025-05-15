package hr.java.exception;

/**
 * The {@code DatabaseException} class is a custom exception that extends {@link RuntimeException}.
 * It is used to signal errors related to database operations.
 */
public class DatabaseException extends RuntimeException {

    /**
     * Default constructor for the {@code DatabaseException}.
     */
    public DatabaseException() {
    }

    /**
     * Constructs a new {@code DatabaseException} with the specified detail message.
     *
     * @param message the detail message
     */
    public DatabaseException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code DatabaseException} with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause of the exception
     */
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@code DatabaseException} with the specified cause.
     *
     * @param cause the cause of the exception
     */
    public DatabaseException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new {@code DatabaseException} with the specified detail message, cause, suppression
     * enabled or disabled, and writable stack trace enabled or disabled.
     *
     * @param message            the detail message
     * @param cause              the cause of the exception
     * @param enableSuppression  whether or not suppression is enabled
     * @param writableStackTrace whether or not the stack trace is writable
     */
    public DatabaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
