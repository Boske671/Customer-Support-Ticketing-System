package hr.java.data_repository.file_repository;

import hr.java.entity.LoggedInUser;
import hr.java.enums.AgentType;
import hr.java.interfaces.PasswordHasher;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * The LoggedInUserManager class manages a list of logged-in users and
 * provides methods to check user existence based on credentials.
 */
public final class LoggedInUserManager implements PasswordHasher {

    private List<LoggedInUser> users;

    /**
     * Instantiates a new LoggedInUserManager and loads users from the text file.
     */
    public LoggedInUserManager() {
        List<LoggedInUser> readingFromFileLoggedInUserList = LoggedInUserDataRepository.readFromTextFile();
        this.users = new ArrayList<>();
        this.users.addAll(readingFromFileLoggedInUserList);
    }

    /**
     * Gets the list of logged-in users.
     *
     * @return the list of {@link LoggedInUser} objects
     */
    public List<LoggedInUser> getUsers() {
        return users;
    }

    /**
     * Sets the list of logged-in users.
     *
     * @param users the list of {@link LoggedInUser} objects to set
     */
    public void setUsers(List<LoggedInUser> users) {
        this.users = users;
    }

    /**
     * Checks if a user with the given credentials exists and returns their {@link AgentType}.
     *
     * @param firstName the first name of the user
     * @param lastName  the last name of the user
     * @param email     the email of the user
     * @param password  the password of the user (hashed before comparison)
     * @return the {@link AgentType} of the user if found, otherwise null
     * @throws NoSuchAlgorithmException if an error occurs while hashing the password
     */
    public AgentType exists(String firstName, String lastName, String email, String password) throws NoSuchAlgorithmException {
        for (LoggedInUser user : users) {
            if (user.getName().equals(firstName) &&
                    user.getLastName().equals(lastName) &&
                    user.getEmail().equals(email) &&
                    user.getPassword().equals(encryptString(password))) {
                return user.getType();
            }
        }
        return null;
    }
}
