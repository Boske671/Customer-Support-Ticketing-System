package hr.java.exception;


/**
 * The {@code UserInputLengthException} class is a custom exception that extends {@link Exception}.
 * It is used to handle cases where user input exceeds or does not meet the expected length criteria.
 */
public class UserInputLengthException extends Exception {

    /**
     * Default constructor for the {@code UserInputLengthException}.
     */
    public UserInputLengthException() {
    }

    /**
     * Constructs a new {@code UserInputLengthException} with the specified detail message.
     *
     * @param message the detail message
     */
    public UserInputLengthException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code UserInputLengthException} with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause of the exception
     */
    public UserInputLengthException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@code UserInputLengthException} with the specified cause.
     *
     * @param cause the cause of the exception
     */
    public UserInputLengthException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new {@code UserInputLengthException} with the specified detail message, cause, suppression
     * enabled or disabled, and writable stack trace enabled or disabled.
     *
     * @param message            the detail message
     * @param cause              the cause of the exception
     * @param enableSuppression  whether or not suppression is enabled
     * @param writableStackTrace whether or not the stack trace is writable
     */
    public UserInputLengthException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
