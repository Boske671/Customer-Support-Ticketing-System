package hr.java.entity;

import hr.java.enums.AgentType;

import java.io.Serializable;

/**
 * The {@code LoggedInUser} class represents a user who is logged in to the system.
 * It contains the user's name, last name, email, password, and their associated {@link AgentType}.
 */
public class LoggedInUser implements Serializable {

    private String name;
    private String lastName;
    private String email;
    private String password;
    private AgentType type;

    /**
     * Instantiates a new {@code LoggedInUser} with the specified details.
     *
     * @param name     the name of the user
     * @param lastName the last name of the user
     * @param email    the email of the user
     * @param password the password of the user
     * @param type     the {@link AgentType} of the user
     */
    public LoggedInUser(String name, String lastName, String email, String password, AgentType type) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.type = type;
    }

    /**
     * Gets the name of the logged-in user.
     *
     * @return the name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the last name of the logged-in user.
     *
     * @return the last name of the user
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Gets the email of the logged-in user.
     *
     * @return the email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the password of the logged-in user.
     *
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the {@link AgentType} of the logged-in user.
     *
     * @return the {@link AgentType} of the user
     */
    public AgentType getType() {
        return type;
    }

    /**
     * Sets the name of the logged-in user.
     *
     * @param name the new name of the user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the last name of the logged-in user.
     *
     * @param lastName the new last name of the user
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Sets the email of the logged-in user.
     *
     * @param email the new email of the user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the password of the logged-in user.
     *
     * @param password the new password of the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets the {@link AgentType} of the logged-in user.
     *
     * @param type the new {@link AgentType} of the user
     */
    public void setType(AgentType type) {
        this.type = type;
    }
}
