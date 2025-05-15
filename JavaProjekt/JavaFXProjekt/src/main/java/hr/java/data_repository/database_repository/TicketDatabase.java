package hr.java.data_repository.database_repository;

import hr.java.entity.Ticket;
import hr.java.enums.TicketPriority;
import hr.java.enums.TicketStatus;
import hr.java.exception.DatabaseException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.example.javafxprojekt.main.Main.logger;

/**
 * The type Ticket database.
 */
public class TicketDatabase extends Database {

    /**
     * Adds a new ticket to the database.
     *
     * @param ticket the ticket to be added to the database
     * @return the ID of the newly added ticket
     * @throws DatabaseException if an error occurs during database operations
     */
    public static Long addTicket(Ticket ticket) {
        String sql = "INSERT INTO Ticket (customer_id, agent_id, summary, description, status, priority, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = openConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, ticket.getCustomerID());
            if (ticket.getAssignedAgentID() != null) {
                statement.setLong(2, ticket.getAssignedAgentID());
            } else {
                statement.setNull(2, java.sql.Types.BIGINT);
            }
            statement.setString(3, ticket.getSummary());
            statement.setString(4, ticket.getDescription());
            statement.setString(5, ticket.getTicketStatus().toString());
            statement.setString(6, ticket.getTicketPriority().toString());
            statement.setObject(7, ticket.getDateCreated());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                logger.error("Creating ticket failed, no rows affected.");
                throw new SQLException("Creating ticket failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    logger.error("Could not add ticket to the database and could not find an ID!");
                    throw new DatabaseException("Could not add ticket to the database and could not find an ID!");
                }
            }
        } catch (SQLException e) {
            logger.error("Could not add ticket to the database!");
            throw new DatabaseException("Could not add ticket to the database!");
        }
    }

    /**
     * Gets all tickets from the database.
     *
     * @return the list of all tickets
     * @throws DatabaseException if an error occurs during database operations
     */
    public static List<Ticket> getAllTickets() {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT TICKET_ID, CUSTOMER_ID, AGENT_ID, SUMMARY, DESCRIPTION, STATUS, PRIORITY, CREATED_AT, RESOLVED_AT FROM TICKET";
        try (Connection connection = openConnection();
             Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            resultSetMethod(tickets, rs);
        } catch (SQLException e) {
            logger.error("Could not get all tickets!");
            throw new DatabaseException("Could not get all tickets!");
        }
        return tickets;
    }

    /**
     * Update ticket column with new provided value
     *
     * @param column   the column to update
     * @param newValue the new value for the column
     * @param ticketID the ticket id to update
     * @throws DatabaseException if an error occurs during database operations
     */
    public static void updateTicket(String column, String newValue, Long ticketID) {
        String sql = "UPDATE TICKET SET " + column + " = ? WHERE ticket_id = ?";
        try (Connection connection = openConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, newValue);
            stmt.setLong(2, ticketID);
            stmt.executeUpdate();

        } catch (SQLException e) {
            logger.error("Could not update ticket!");
            throw new DatabaseException("Could not update ticket with ID: " + ticketID + "!");
        }
    }

    /**
     * Deletes a ticket from the database.
     *
     * @param ticketID the ticket id to delete
     * @throws DatabaseException if an error occurs during database operations
     */
    public static void deleteTicket(Long ticketID) {
        String sql = "DELETE FROM TICKET WHERE ticket_id = ?";
        try (Connection connection = openConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, ticketID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Could not delete ticket with ID: {} ", ticketID);
            throw new DatabaseException("Could not delete ticket with ID: " + ticketID + "!");
        }
    }

    /**
     * Gets unassigned tickets.
     *
     * @return the unassigned tickets
     * @throws DatabaseException if an error occurs during database operations
     */
    public static List<Ticket> getUnassignedTickets() {
        String sql = "SELECT TICKET_ID, CUSTOMER_ID, AGENT_ID, SUMMARY, DESCRIPTION, STATUS, PRIORITY, CREATED_AT, RESOLVED_AT FROM TICKET  WHERE status = ?";
        List<Ticket> tickets = new ArrayList<>();
        try (Connection connection = openConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "Open");
            return getTickets(tickets, stmt);
        } catch (SQLException e) {
            logger.error("Could not get unassigned tickets!");
            throw new DatabaseException("Could not get unassigned tickets!");
        }
    }

    /**
     * Retrieves tickets from the database using a prepared statement.
     *
     * @param tickets the list to store retrieved tickets
     * @param stmt    the prepared statement
     * @return the list of retrieved tickets
     * @throws SQLException if a database access error occurs
     */
    private static List<Ticket> getTickets(List<Ticket> tickets, PreparedStatement stmt) throws SQLException {
        try (ResultSet rs = stmt.executeQuery()) {
            resultSetMethod(tickets, rs);
        }
        return tickets;
    }

    /**
     * Maps a ResultSet to a list of Ticket objects.
     *
     * @param tickets the list to store retrieved tickets
     * @param rs      the result set
     * @throws SQLException if a database access error occurs
     */
    private static void resultSetMethod(List<Ticket> tickets, ResultSet rs) throws SQLException {
        while (rs.next()) {
            Long id = rs.getLong("ticket_id");
            Long customerID = rs.getLong("customer_id");
            Long agentID = rs.getLong("agent_id");
            String summary = rs.getString("summary");
            String description = rs.getString("description");
            TicketStatus status = TicketStatus.valueOf(rs.getString("status").toUpperCase());
            TicketPriority priority = TicketPriority.valueOf(rs.getString("priority").toUpperCase());
            LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
            LocalDateTime resolvedAt = null;
            Timestamp resolvedAtTimestamp = rs.getTimestamp("resolved_at");
            if (resolvedAtTimestamp != null) {
                resolvedAt = resolvedAtTimestamp.toLocalDateTime();
            }
            Ticket ticket = new Ticket(id, summary, description, status, priority, agentID, customerID, createdAt, resolvedAt);
            tickets.add(ticket);
        }
    }

    /**
     * Gets in progress tickets.
     *
     * @return the in progress tickets
     * @throws DatabaseException if an error occurs during database operations
     */
    public static List<Ticket> getInProgressTickets() {
        String sql = "SELECT TICKET_ID, CUSTOMER_ID, AGENT_ID, SUMMARY, DESCRIPTION, STATUS, PRIORITY, CREATED_AT, RESOLVED_AT FROM TICKET WHERE status = ?";
        List<Ticket> tickets = new ArrayList<>();
        try (Connection connection = openConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "IN_PROGRESS");
            return getTickets(tickets, stmt);
        } catch (SQLException e) {
            logger.error("Could not get in-progress tickets!");
            throw new DatabaseException("Could not get in-progress tickets!");
        }
    }


    /**
     * Resolves ticket.
     *
     * @param ticket the ticket
     * @throws DatabaseException if an error occurs during database operations
     */
    public static void resolveTicket(Ticket ticket) {
        String sql = "UPDATE Ticket SET status = ?, RESOLVED_AT = ? WHERE ticket_id = ?";
        ticket.setDateResolved(LocalDateTime.now());
        try (Connection connection = openConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, "CLOSED");
            stmt.setTimestamp(2, java.sql.Timestamp.valueOf(ticket.getDateResolved()));
            stmt.setLong(3, ticket.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Could not resolve ticket!");
            throw new DatabaseException("Could not resolve ticket with ID: " + ticket.getId() + "!", e);
        }
    }


    /**
     * Gets tickets by agent id.
     *
     * @param agentId the agent id used for getting the tickets
     * @return the tickets assigned to agent id
     * @throws DatabaseException if an error occurs during database operations
     */
    public static List<Ticket> getTicketsByAgentId(Long agentId) {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT TICKET_ID, CUSTOMER_ID, AGENT_ID, SUMMARY, DESCRIPTION, STATUS, PRIORITY, CREATED_AT, RESOLVED_AT FROM TICKET WHERE AGENT_ID = ?";

        try (Connection connection = openConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, agentId);
            ResultSet rs = stmt.executeQuery();
            resultSetMethod(tickets, rs);
        } catch (SQLException e) {
            logger.error("Could not get tickets by agent!");
            throw new DatabaseException("Could not get tickets with Agent ID: " + agentId + "!");
        }
        return tickets;
    }
}
