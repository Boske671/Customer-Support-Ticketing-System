package hr.java.data_repository.database_repository;
import hr.java.exception.DatabaseException;
import hr.java.exception.FileException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

import static org.example.javafxprojekt.main.Main.logger;

/**
 * Utility class for database connections.
 */
public class Database
{

    /**
     * Private constructor to prevent instantiation.
     */
    Database() {}

    /**
     * Opens a database connection using information from "database.properties".
     *
     * @return a database {@link Connection}.
     * @throws FileException if the properties file cannot be loaded.
     * @throws DatabaseException if the connection fails.
     */
    public static Connection openConnection()
    {
        Properties props = new Properties();
        try (FileReader reader = new FileReader("database.properties"))
        {
            props.load(reader);
        } catch (IOException e)
        {
            logger.error("Could not open database.properties!");
            throw new FileException("Could not load database.properties!");
        }
        String databaseUrl = props.getProperty("databaseUrl");
        String username = props.getProperty("username");
        String password = props.getProperty("password");
        try
        {
            return DriverManager.getConnection(databaseUrl, username, password);
        } catch (SQLException e)
        {
            logger.error("Could not open database connection!");
            throw new DatabaseException("Could not connect to database!");
        }
    }
}