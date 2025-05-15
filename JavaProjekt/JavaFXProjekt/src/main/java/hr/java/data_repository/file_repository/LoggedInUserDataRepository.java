package hr.java.data_repository.file_repository;

import hr.java.entity.Agent;
import hr.java.exception.FileException;
import hr.java.file_paths.FilePath;
import hr.java.entity.LoggedInUser;
import hr.java.enums.AgentType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.example.javafxprojekt.main.Main.logger;

/**
 * The class LoggedInUserDataRepository class provides methods for saving, writing, reading and updating
 * logged-in users
 */
public class LoggedInUserDataRepository
{
    private LoggedInUserDataRepository()
    {
    }

    /**
     * Saves a single logged-in user to a text file (appends to the file)
     *
     * @param loggedInUser the {@link LoggedInUser} object to be saved
     * @throws FileException if an error occurs while saving the user data
     */
    public static void saveToTextFile(LoggedInUser loggedInUser)
    {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FilePath.LOGGED_IN_USERS.getPath(), true)))
        {
            writeUser(loggedInUser, bw);
        } catch (IOException e)
        {
            logger.error("Could not save logged in user!");
            throw new FileException("Could not save logged in user, " + e.getMessage());
        }
    }

    /**
     * Writes the user details to a BufferedWriter (used for other methods)
     *
     * @param loggedInUser the {@link LoggedInUser} object to be written
     * @param bw           the {@link BufferedWriter} to write the data
     * @throws IOException if an error occurs while writing to the file
     */
    private static void writeUser(LoggedInUser loggedInUser, BufferedWriter bw) throws IOException
    {
        bw.write(loggedInUser.getName());
        bw.newLine();
        bw.write(loggedInUser.getLastName());
        bw.newLine();
        bw.write(loggedInUser.getEmail());
        bw.newLine();
        bw.write(loggedInUser.getPassword());
        bw.newLine();
        bw.write(loggedInUser.getType().toString());
        bw.newLine();
    }

    /**
     * Reads the logged-in users from a text file.
     *
     * @return a list of {@link LoggedInUser} objects read from the file
     * @throws FileException if an error occurs while reading the data
     */
    public static List<LoggedInUser> readFromTextFile()
    {
        List<LoggedInUser> loggedInUsers = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FilePath.LOGGED_IN_USERS.getPath())))
        {
            String line;
            while ((line = br.readLine()) != null)
            {
                String firstName = line;
                String lastName = br.readLine();
                String email = br.readLine();
                String password = br.readLine();
                String type = br.readLine();
                AgentType type2 = AgentType.valueOf(type);
                loggedInUsers.add(new LoggedInUser(firstName, lastName, email, password, type2));
            }
        } catch (IOException e)
        {
            logger.error("Could not read logged in user from binary file!");
            throw new FileException("Could not read logged in user, " + e.getMessage());
        }
        return loggedInUsers;
    }

    /**
     * Saves a list of logged-in users to a text file.
     *
     * @param loggedInUsers the list of {@link LoggedInUser} objects to be saved
     * @throws FileException if an error occurs while saving the user data
     */
    public static void saveToTextFile(List<LoggedInUser> loggedInUsers)
    {
        try (BufferedWriter bw = new BufferedWriter((new FileWriter(FilePath.LOGGED_IN_USERS.getPath()))))
        {
            for (LoggedInUser loggedInUser : loggedInUsers)
            {
                writeUser(loggedInUser, bw);
            }
        } catch (IOException e)
        {
            logger.error("Could not save logged in users!");
            throw new FileException("Could not save logged in user, " + e.getMessage());
        }
    }

    /**
     * Updates the logged-in user's information based on an {@link Agent} object.
     * If a user with the same email as the agent is found, their first name and
     * last name are updated.
     *
     * @param agent the {@link Agent} object containing updated information
     * @throws FileException if an error occurs while updating the user data
     */
    public static void updateLoggedInUser(Agent agent)
    {
        List<LoggedInUser> loggedInUsers = readFromTextFile();
        for (LoggedInUser loggedInUser : loggedInUsers)
        {
            if (loggedInUser.getEmail().equals(agent.getEmail()))
            {
                loggedInUser.setName(agent.getFirstName());
                loggedInUser.setLastName(agent.getLastName());
            }
        }
        saveToTextFile(loggedInUsers);
    }
}

