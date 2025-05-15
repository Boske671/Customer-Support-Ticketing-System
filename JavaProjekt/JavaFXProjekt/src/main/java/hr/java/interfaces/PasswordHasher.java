package hr.java.interfaces;

import hr.java.data_repository.file_repository.LoggedInUserManager;
import org.example.javafxprojekt.controllers.add_controllers.AddAgentController;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The PasswordHasher interface provides a method for hashing passwords using SHA-256.
 * It is a sealed interface that permits {@link AddAgentController} {@link LoggedInUserManager} classes
 */
public sealed interface PasswordHasher permits AddAgentController, LoggedInUserManager {

    /**
     * Encrypts a given string using the SHA-256 hashing algorithm.
     *
     * @param string the input string to be encrypted
     * @return the hashed string in hexadecimal format
     * @throws NoSuchAlgorithmException if the SHA-256 algorithm is not available
     */
    public default String encryptString(String string) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] messageDigest = md.digest(string.getBytes());
        BigInteger no = new BigInteger(1, messageDigest);
        return no.toString(16);
    }
}
