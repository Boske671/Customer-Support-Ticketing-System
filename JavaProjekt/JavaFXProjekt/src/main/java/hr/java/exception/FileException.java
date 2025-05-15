package hr.java.exception;

/**
 * The {@code FileException} class is a custom exception that extends {@link RuntimeException}.
 * It is used to signal errors related to file operations.
 */
public class FileException extends RuntimeException {

    /**
     * Default constructor for the {@code FileException}.
     */
    public FileException() {
    }

    /**
     * Constructs a new {@code FileException} with the specified detail message.
     *
     * @param message the detail message
     */
    public FileException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code FileException} with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause of the exception
     */
    public FileException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@code FileException} with the specified cause.
     *
     * @param cause the cause of the exception
     */
    public FileException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new {@code FileException} with the specified detail message, cause, suppression
     * enabled or disabled, and writable stack trace enabled or disabled.
     *
     * @param message            the detail message
     * @param cause              the cause of the exception
     * @param enableSuppression  whether or not suppression is enabled
     * @param writableStackTrace whether or not the stack trace is writable
     */
    public FileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
