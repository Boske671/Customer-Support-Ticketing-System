package hr.java.data_repository.database_repository;

import hr.java.entity.Agent;
import hr.java.data_repository.file_repository.LoggedInUserDataRepository;
import hr.java.entity.LoggedInUser;
import hr.java.enums.AgentStatus;
import hr.java.enums.AgentType;
import hr.java.exception.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.example.javafxprojekt.main.Main.logger;

/**
 * Repository for storing and retrieving agent data from a database.
 *
 * This class provides methods for adding, getting, updating, and deleting agents,
 * as well as assigning tickets to agents.
 *
 */
public class AgentDatabase extends Database
{
    /**
     * Column names for the AGENT table.
     */
    private static String firstname = "firstname";
    private static String lastname = "lastname";
    private static String email = "email";
    private static String agenttype = "agenttype";

    /**
     * Private constructor to prevent instantiation.
     */
    private AgentDatabase() {
        super();
    }

    /**
     * Adds a new agent to the database.
     *
     * @param agent the agent to add
     * @throws DatabaseException if an error occurs during database operations
     */
    public static void addNewAgent(Agent agent)
    {
        String sql = "INSERT INTO AGENT(FIRSTNAME, LASTNAME, EMAIL, AGENTTYPE) VALUES(?, ?, ?, ?)";
        try (Connection connection = openConnection();
             PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setString(1, agent.getFirstName());
            stmt.setString(2, agent.getLastName());
            stmt.setString(3, agent.getEmail());
            stmt.setString(4, agent.getAgentType().toString());
            stmt.executeUpdate();
        } catch (SQLException e)
        {
            logger.error("Could not add agent to the database! {} ", e.getMessage());
            throw new DatabaseException("Could not add agent to the database!");
        }
    }


    /**
     * Retrieves all agents from the database.
     *
     * @return a list of all agents
     * @throws DatabaseException if an error occurs during database operations
     */
    public static List<Agent> getAllAgents()
    {
        List<Agent> agents = new ArrayList<>();
        String sql = "SELECT ID, FIRSTNAME, LASTNAME, EMAIL, AGENTTYPE FROM AGENT";
        try (Connection connection = openConnection();
             Statement stmt = connection.createStatement())
        {

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next())
            {
                Long id = rs.getLong("ID");
                String firstName = rs.getString(firstname);
                String lastName = rs.getString(lastname);
                String email = rs.getString(AgentDatabase.email);
                String agentType = rs.getString(agenttype);
                Agent agent = new Agent(id, firstName, lastName, email, AgentStatus.AVAILABLE, AgentType.valueOf(agentType));
                agents.add(agent);
            }
        } catch (SQLException e)
        {
            logger.error("Could not get all agents! {} ", e.getMessage());
            throw new DatabaseException("Could not get all agents!");
        }
        return agents;
    }

    /**
     * Checks if an agent with the given email already exists in the database.
     *
     * @param agent the agent to check
     * @return true if the agent already exists, false otherwise
     * @throws DatabaseException if an error occurs during database operations
     */
    public static boolean agentAlreadyExists(Agent agent)
    {
        String sql = "SELECT 1 FROM AGENT WHERE EMAIL = ?";
        try (Connection connection = openConnection();
             PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setString(1, agent.getEmail());
            try (ResultSet rs = stmt.executeQuery())
            {
                return rs.next();
            }
        } catch (SQLException e)
        {
            throw new DatabaseException("Could not get agent with id: " + agent.getId() + " by email!");
        }
    }


    /**
     * Updates an agent's information in the database.
     *
     * @param column the column to update
     * @param newValue the new value for the column
     * @param agentID the ID of the agent to update
     * @throws DatabaseException if an error occurs during database operations
     */
    public static void updateAgent(String column, String newValue, Long agentID)
    {
        String sql = "UPDATE AGENT SET " + column + " = ? WHERE id = ?";
        try (Connection connection = openConnection();
             PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setString(1, newValue);
            stmt.setLong(2, agentID);
            stmt.executeUpdate();
        } catch (SQLException e)
        {
            logger.error("Could not update agent {} ", e.getMessage());
            throw new DatabaseException("Could not update agent with id: " + agentID);
        }
    }

    /**
     * Deletes an agent from the database.
     *
     * @param agent the agent to delete
     * @throws DatabaseException if an error occurs during database operations
     */
    public static void deleteAgent(Agent agent)
    {
        List<LoggedInUser> loggedInAgents = LoggedInUserDataRepository.readFromTextFile();
        loggedInAgents.removeIf(loggedInUser -> loggedInUser.getEmail().equals(agent.getEmail()));
        LoggedInUserDataRepository.saveToTextFile(loggedInAgents);
        String sql = "DELETE FROM AGENT WHERE ID = ?";
        try (Connection connection = openConnection();
             PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setLong(1, agent.getId());
            stmt.executeUpdate();
        } catch (SQLException e)
        {
            logger.error("Could not delete agent {} ", e.getMessage());
            throw new DatabaseException("Could not delete agent with ID: " + agent.getId() + "!");
        }
    }


    /**
     * Retrieves an agent by email from the database.
     *
     * @param email the email of the agent to retrieve
     * @return the agent with the specified email
     * @throws DatabaseException if an error occurs during database operations
     */
    public static Agent getAgentByEmail(String email)
    {
        String sql = "SELECT ID, FIRSTNAME, LASTNAME, EMAIL, AGENTTYPE FROM AGENT WHERE EMAIL = ?";
        try (Connection connection = openConnection();
             PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery())
            {
                rs.next();
                Long id = rs.getLong("ID");
                String firstName = rs.getString("FIRSTNAME");
                String lastName = rs.getString("LASTNAME");
                String agentType = rs.getString("AGENTTYPE");
                String agentEmail = rs.getString("EMAIL");
                Result result = new Result(id, firstName, lastName, agentType, agentEmail);
                return new Agent(result.id(), result.firstName(), result.lastName(), result.agentEmail(), AgentStatus.AVAILABLE, AgentType.valueOf(result.agentType()));
            }
        } catch (SQLException e)
        {
            logger.error("Could not get agent with email: {}", email);
            throw new DatabaseException("Could not get agent with email: " + email + "!");
        }
    }

    private record Result(Long id, String firstName, String lastName, String agentType, String agentEmail) {
    }


    /**
     * Assigns a ticket to an agent in the database.
     *
     * @param ticketID the ID of the ticket to assign
     * @param agentID the ID of the agent to assign the ticket to
     * @throws DatabaseException if an error occurs during database operations
     */
    public static void assignTicketToAgent(Long ticketID, Long agentID)
    {
        String sql = "UPDATE Ticket SET agent_id = ?, status = ? WHERE ticket_id = ?";
        try (Connection connection = openConnection();
             PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setLong(1, agentID);
            stmt.setString(2, "IN_PROGRESS");
            stmt.setLong(3, ticketID);

            stmt.executeUpdate();
        } catch (SQLException e)
        {
            logger.error("Could not assign ticket to agent {} ", e.getMessage());
            throw new DatabaseException("Could not assign ticket with ID: " + ticketID + " to agent with ID: " + agentID + "!");
        }
    }

    /**
     * Retrieves an agent by ticket ID from the database.
     *
     * @param ticketID the ID of the ticket to retrieve the agent for
     * @return the agent assigned to the ticket with the specified ID
     * @throws DatabaseException if an error occurs during database operations
     */
    public static Agent getAgentByTicketID(Long ticketID)
    {
        String sql = "SELECT a.* FROM Agent a INNER JOIN Ticket t ON a.id = t.agent_id WHERE t.ticket_id = ?";

        try (Connection connection = openConnection();
             PreparedStatement stmt = connection.prepareStatement(sql))
        {

            stmt.setLong(1, ticketID);
            try (ResultSet rs = stmt.executeQuery())
            {
                if (rs.next())
                {
                    Long id = rs.getLong("ID");
                    String firstName = rs.getString("FIRSTNAME");
                    String lastName = rs.getString("LASTNAME");
                    String email = rs.getString("EMAIL");
                    String agentType = rs.getString("AGENTTYPE");

                    return new Agent(id, firstName, lastName, email, AgentStatus.AVAILABLE, AgentType.valueOf(agentType));
                }
            }
        } catch (SQLException e)
        {
            logger.error("Could not get agent with ID: {}", ticketID);
            throw new DatabaseException("Could not find an agent for ticket ID: " + ticketID);
        }
        return null;
    }

}
