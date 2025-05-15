package hr.java.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * The {@code Customer} record represents a customer entity that extends a {@link Person}
 * and includes an email field. It is immutable and implements {@link Serializable}.
 *
 * @param person        the {@link Person} instance associated with the customer
 * @param customerEmail the email address of the customer
 */
public record Customer(Person person, String customerEmail) implements Serializable {

    /**
     * Gets the unique identifier of the customer.
     *
     * @return the customer’s ID
     */
    public long getId() {
        return person.getId();
    }

    /**
     * Gets the first name of the customer.
     *
     * @return the customer's first name
     */
    public String getFirstName() {
        return person.getFirstName();
    }

    /**
     * Gets the last name of the customer.
     *
     * @return the customer's last name
     */
    public String getLastName() {
        return person.getLastName();
    }

    /**
     * Gets the email address of the customer.
     *
     * @return the customer's email address
     */
    public String getCustomerEmail() {
        return customerEmail;
    }

    /**
     * Compares this customer to another object for equality based on email, first name, and last name.
     *
     * @param o the object to compare
     * @return {@code true} if the objects are equal, otherwise {@code false}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(customerEmail, customer.customerEmail) &&
                Objects.equals(getFirstName(), customer.getFirstName()) &&
                Objects.equals(getLastName(), customer.getLastName());
    }

    /**
     * Generates a hash code for the customer based on the email.
     *
     * @return the hash code of the customer
     */
    @Override
    public int hashCode() {
        return Objects.hash(customerEmail);
    }
}