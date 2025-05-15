package hr.java.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The {@code EmailValidator} class provides a utility method to validate email addresses
 * using a regular expression. The class is designed to be used in a static context
 * and cannot be instantiated.
 */
public class EmailValidator
{
    private EmailValidator() {}

    /**
     * A regular expression pattern used to validate email addresses.
     * The pattern checks for a standard email format such as "example@example.com".
     * The validation is case insensitive.
     */
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    /**
     * Validates an email address based on the {@link #VALID_EMAIL_ADDRESS_REGEX} pattern.
     *
     * @param email the email address to be validated
     * @return {@code true} if the email address matches the pattern, {@code false} otherwise
     */
    public static boolean isValidEmail(String email)
    {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.matches();
    }
}