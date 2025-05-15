package hr.java.entity;

import java.io.Serializable;

/**
 * The class Person holds information as firstName and last name
 * implements the Serializable interface for Serialiazition
 */
public class Person extends Entity implements Serializable
{
    private String firstName;
    private String lastName;

    /**
     * Instantiates a new Person.
     *
     * @param id        the id
     * @param firstName the first name
     * @param lastName  the last name
     */
    public Person(long id, String firstName, String lastName)
    {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
    }


    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName()
    {
        return firstName;
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName()
    {
        return lastName;
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

}
