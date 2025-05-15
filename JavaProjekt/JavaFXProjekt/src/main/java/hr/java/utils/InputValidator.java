package hr.java.utils;

import hr.java.exception.InvalidUserInputException;
import hr.java.exception.UserInputLengthException;

import static org.example.javafxprojekt.main.Main.logger;

/**
 * The {@code InputValidator} class provides utility methods to validate user input,
 * such as checking string length and validating email format.
 * This class cannot be instantiated.
 */
public class InputValidator
{
    private InputValidator() {}

    /**
     * Validates the length of a string input.
     * Throws an exception if the input is too long or empty.
     *
     * @param field  the name of the field being validated
     * @param input  the input string to validate
     * @param length the maximum allowed length for the input
     * @throws UserInputLengthException if the input is too long or empty
     */
    public static void lengthValidator(String field, String input, int length) throws UserInputLengthException
    {
        if (input.length() > length)
        {
            logger.error("Input length exceeds maximum length of {}", length);
            throw new UserInputLengthException(field + " too long, max length is " + length + "!");
        }
        if (input.isEmpty())
        {
            logger.error("Input is empty!");
            throw new UserInputLengthException(field + " can't be empty!");
        }
    }

    /**
     * Validates if the input string is a valid email address.
     * Throws an exception if the email format is invalid.
     *
     * @param input the email string to validate
     * @throws InvalidUserInputException if the email format is invalid
     */
    public static void emailValidator(String input) throws InvalidUserInputException
    {
        if (!EmailValidator.isValidEmail(input))
        {
            logger.error("Email is not valid!");
            throw new InvalidUserInputException("Invalid email address!");
        }
    }

    /**
     * Validates that the input string does not exceed a specified length.
     * Throws an exception if the input is too long.
     *
     * @param field  the name of the field being validated
     * @param input  the input string to validate
     * @param length the maximum allowed length for the input
     * @throws UserInputLengthException if the input is too long
     */
    public static void onlyLengthValidation(String field, String input, int length) throws UserInputLengthException
    {
        if (input.length() > length)
        {
            logger.error("Input length exceeds maximum length  of {}", length);
            throw new UserInputLengthException(field + " too long, max length is " + length + "!");
        }
    }
}
