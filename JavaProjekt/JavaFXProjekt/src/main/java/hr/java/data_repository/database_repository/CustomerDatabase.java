package hr.java.data_repository.database_repository;

import hr.java.entity.Customer;
import hr.java.entity.Person;
import hr.java.exception.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.example.javafxprojekt.main.Main.logger;

/**
 * Repository for storing and retrieving customer data from a database.
 *
 * This class provides methods for adding new customers to the database.
 *
 */
public class CustomerDatabase extends Database
{
    /**
     * Adds a new customer to the database.
     *
     * @param customer the customer to add
     * @return the ID of the newly added customer
     * @throws DatabaseException if an error occurs during database operations
     */
    public static Long addNewCustomer(Customer customer)
    {
        String sql = "INSERT INTO CUSTOMER(FIRSTNAME, LASTNAME, EMAIL) VALUES(?, ?, ?)";
        try (Connection connection = openConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            stmt.setString(1, customer.getFirstName());
            stmt.setString(2, customer.getLastName());
            stmt.setString(3, customer.getCustomerEmail());
            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys())
            {
                if (generatedKeys.next())
                {
                    return generatedKeys.getLong(1);
                } else
                {
                    logger.error("Could not return generated key for new customer!");
                    throw new DatabaseException("Could not return generated key for new customer!");
                }
            }
        } catch (SQLException e)
        {
            logger.error("Could not add new customer: {}", e.getMessage());
            throw new DatabaseException("Could not add customer to the database!");
        }
    }

    /**
     * Checks if a customer with the given email already exists in the database.
     *
     * @param customer the customer to check
     * @return true if the customer already exists, false otherwise
     * @throws DatabaseException if an error occurs during database operations
     */
    public static boolean doesCustomerExist(Customer customer)
    {
        String sql = "SELECT 1 FROM CUSTOMER WHERE EMAIL = ?";
        try (Connection connection = openConnection();
             PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setString(1, customer.getCustomerEmail());
            try (ResultSet rs = stmt.executeQuery())
            {
                return rs.next();
            }
        } catch (SQLException e)
        {
            logger.error("Could not check if customer exists! {}", e.getMessage());
            throw new DatabaseException("Could not check if customer exists!");
        }
    }

    /**
     * Retrieves the ID of a customer by their email.
     *
     * @param customer the customer to retrieve the ID for
     * @return the ID of the customer with the specified email
     * @throws DatabaseException if an error occurs during database operations
     */
    public static Long idFromCustomerEmail(Customer customer)
    {
        String sql = "SELECT ID FROM CUSTOMER WHERE EMAIL = ?";
        try (Connection connection = openConnection();
             PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setString(1, customer.getCustomerEmail());
            try (ResultSet rs = stmt.executeQuery())
            {
                if (rs.next())
                {
                    return rs.getLong("ID");
                }
            }
        } catch (SQLException e)
        {
            logger.error("Error while checking if customer exists: {}", e.getMessage());
            throw new DatabaseException("Could not check if customer exists!");
        }
        return null;
    }

    /**
     * Retrieves all customers from the database.
     *
     * @return a list of all customers
     * @throws DatabaseException if an error occurs during database operations
     */
    public static Set<Customer> getAllCustomers()
    {
        Set<Customer> customers = new HashSet<>();
        String sql = "SELECT ID, FIRSTNAME, LASTNAME, EMAIL FROM CUSTOMER";
        try (Connection connection = openConnection();
             Statement stmt = connection.createStatement())
        {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next())
            {
                Long id = rs.getLong("ID");
                String firstName = rs.getString("FIRSTNAME");
                String lastName = rs.getString("LASTNAME");
                String email = rs.getString("EMAIL");
                Customer customer = new Customer(new Person(id, firstName, lastName), email);
                customers.add(customer);
            }
        } catch (SQLException e)
        {
            logger.error("Could not get all customers: {}", e.getMessage());
            throw new DatabaseException("Could not get all customers!");
        }
        return customers;
    }

    /**
     * Retrieves a customer by their ID.
     *
     * @param customerID the ID of the customer to retrieve
     * @return the customer with the specified ID
     * @throws DatabaseException if an error occurs during database operations
     */
    public static Customer getCustomerById(Long customerID)
    {
        String sql = "SELECT ID, FIRSTNAME, LASTNAME, EMAIL FROM CUSTOMER WHERE ID = ?";
        try (Connection connection = openConnection();
             PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setLong(1, customerID);
            try (ResultSet rs = stmt.executeQuery())
            {
                if (rs.next())
                {
                    Long id = rs.getLong("ID");
                    String firstName = rs.getString("FIRSTNAME");
                    String lastName = rs.getString("LASTNAME");
                    String email = rs.getString("EMAIL");
                    return new Customer(new Person(id, firstName, lastName), email);
                }
            }
        } catch (SQLException e)
        {
            logger.error("Could not get customer with ID: {}", customerID);
            throw new DatabaseException("Could not get customer by ID!");
        }
        return null;
    }

    /**
     * Deletes a customer from the database.
     *
     * @param customerID the ID of the customer to delete
     * @throws DatabaseException if an error occurs during database operations
     */
    public static void deleteCustomer(Long customerID)
    {
        String sql = "DELETE FROM CUSTOMER WHERE ID = ?";
        try (Connection connection = openConnection();
             PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setLong(1, customerID);
            stmt.executeUpdate();
        } catch (SQLException e)
        {
            logger.error("Could not delete customer with ID: {}", customerID);
            throw new DatabaseException("Could not delete customer with ID: " + customerID + "!");
        }
    }
}
