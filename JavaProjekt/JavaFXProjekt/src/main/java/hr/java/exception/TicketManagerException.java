package hr.java.exception;


/**
 * The {@code TicketManagerException} class is a custom exception that extends {@link RuntimeException}.
 * It is used to indicate issues related to ticket management operations.
 */
public class TicketManagerException extends RuntimeException {

    /**
     * Default constructor for the {@code TicketManagerException}.
     */
    public TicketManagerException() {
    }

    /**
     * Constructs a new {@code TicketManagerException} with the specified detail message.
     *
     * @param message the detail message
     */
    public TicketManagerException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code TicketManagerException} with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause of the exception
     */
    public TicketManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@code TicketManagerException} with the specified cause.
     *
     * @param cause the cause of the exception
     */
    public TicketManagerException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new {@code TicketManagerException} with the specified detail message, cause, suppression
     * enabled or disabled, and writable stack trace enabled or disabled.
     *
     * @param message            the detail message
     * @param cause              the cause of the exception
     * @param enableSuppression  whether or not suppression is enabled
     * @param writableStackTrace whether or not the stack trace is writable
     */
    public TicketManagerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}