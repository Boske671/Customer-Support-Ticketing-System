package hr.java.entity;

import hr.java.enums.TicketPriority;
import hr.java.enums.TicketStatus;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * The {@code Ticket} class represents a support ticket in the system.
 * It contains information about the ticket's summary, description, status, priority, and
 * related entities such as the assigned agent and the customer.
 * The ticket also keeps track of when it was created and resolved.
 */
public class Ticket extends Entity implements Serializable {

    private String summary;
    private String description;
    private TicketStatus ticketStatus;
    private TicketPriority ticketPriority;
    private Long assignedAgentID;
    private Long customerID;
    private LocalDateTime dateCreated;
    private LocalDateTime dateResolved;

    /**
     * Instantiates a new {@code Ticket} with the specified details.
     * The {@code dateCreated} is automatically set to the current time.
     *
     * @param id                the ticket ID
     * @param summary           the summary of the ticket
     * @param description       the description of the ticket
     * @param ticketStatus      the current status of the ticket
     * @param ticketPriority    the priority of the ticket
     * @param assignedAgentID   the ID of the agent assigned to the ticket
     * @param customerID        the ID of the customer related to the ticket
     */
    public Ticket(Long id, String summary, String description, TicketStatus ticketStatus, TicketPriority ticketPriority, Long assignedAgentID, Long customerID) {
        super(id);
        this.summary = summary;
        this.description = description;
        this.ticketStatus = ticketStatus;
        this.ticketPriority = ticketPriority;
        this.assignedAgentID = assignedAgentID;
        this.customerID = customerID;
        this.dateCreated = LocalDateTime.now();
        this.dateResolved = null;
    }

    /**
     * Instantiates a new {@code Ticket} with the specified details, including creation and resolution dates.
     *
     * @param id                the ticket ID
     * @param summary           the summary of the ticket
     * @param description       the description of the ticket
     * @param ticketStatus      the current status of the ticket
     * @param ticketPriority    the priority of the ticket
     * @param assignedAgentID   the ID of the agent assigned to the ticket
     * @param customerID        the ID of the customer related to the ticket
     * @param dateCreated       the timestamp when the ticket was created
     * @param dateResolved      the timestamp when the ticket was resolved, or {@code null} if not resolved
     */
    public Ticket(Long id, String summary, String description, TicketStatus ticketStatus, TicketPriority ticketPriority, Long assignedAgentID, Long customerID, LocalDateTime dateCreated, LocalDateTime dateResolved) {
        super(id);
        this.summary = summary;
        this.description = description;
        this.ticketStatus = ticketStatus;
        this.ticketPriority = ticketPriority;
        this.assignedAgentID = assignedAgentID;
        this.customerID = customerID;
        this.dateCreated = dateCreated;
        this.dateResolved = dateResolved;
    }

    /**
     * The {@code Builder} class is used to construct {@code Ticket} objects step by step.
     */
    public static class Builder {
        private Long id;
        private String summary;
        private String description;
        private TicketStatus ticketStatus;
        private TicketPriority ticketPriority;
        private Long assignedAgentID;
        private Long customerID;

        /**
         * Sets the ticket ID.
         *
         * @param id the ticket ID
         * @return this builder instance
         */
        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the ticket summary.
         *
         * @param summary the ticket summary
         * @return this builder instance
         */
        public Builder setSummary(String summary) {
            this.summary = summary;
            return this;
        }

        /**
         * Sets the ticket description.
         *
         * @param description the ticket description
         * @return this builder instance
         */
        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        /**
         * Sets the ticket status.
         *
         * @param ticketStatus the ticket status
         * @return this builder instance
         */
        public Builder setTicketStatus(TicketStatus ticketStatus) {
            this.ticketStatus = ticketStatus;
            return this;
        }

        /**
         * Sets the ticket priority.
         *
         * @param ticketPriority the ticket priority
         * @return this builder instance
         */
        public Builder setTicketPriority(TicketPriority ticketPriority) {
            this.ticketPriority = ticketPriority;
            return this;
        }

        /**
         * Sets the ID of the agent assigned to the ticket.
         *
         * @param assignedAgentID the agent's ID
         * @return this builder instance
         */
        public Builder setAssignedAgentID(Long assignedAgentID) {
            this.assignedAgentID = assignedAgentID;
            return this;
        }

        /**
         * Sets the ID of the customer related to the ticket.
         *
         * @param customerID the customer's ID
         * @return this builder instance
         */
        public Builder setCustomerID(Long customerID) {
            this.customerID = customerID;
            return this;
        }

        /**
         * Builds the {@code Ticket} instance using the values provided by the builder.
         *
         * @return the constructed {@code Ticket}
         */
        public Ticket build() {
            return new Ticket(id, summary, description, ticketStatus, ticketPriority, assignedAgentID, customerID);
        }
    }

    /**
     * Gets the ticket summary.
     *
     * @return the ticket summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * Sets the ticket summary.
     *
     * @param summary the new summary
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * Gets the ticket description.
     *
     * @return the ticket description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the ticket description.
     *
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the current status of the ticket.
     *
     * @return the ticket status
     */
    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }

    /**
     * Sets the current status of the ticket.
     *
     * @param ticketStatus the new status
     */
    public void setTicketStatus(TicketStatus ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    /**
     * Gets the priority of the ticket.
     *
     * @return the ticket priority
     */
    public TicketPriority getTicketPriority() {
        return ticketPriority;
    }

    /**
     * Sets the priority of the ticket.
     *
     * @param ticketPriority the new priority
     */
    public void setTicketPriority(TicketPriority ticketPriority) {
        this.ticketPriority = ticketPriority;
    }

    /**
     * Gets the ID of the agent assigned to the ticket.
     *
     * @return the agent's ID
     */
    public Long getAssignedAgentID() {
        return assignedAgentID;
    }

    /**
     * Sets the ID of the agent assigned to the ticket.
     *
     * @param assignedAgentID the new agent's ID
     */
    public void setAssignedAgentID(Long assignedAgentID) {
        this.assignedAgentID = assignedAgentID;
    }

    /**
     * Gets the ID of the customer related to the ticket.
     *
     * @return the customer's ID
     */
    public Long getCustomerID() {
        return customerID;
    }

    /**
     * Sets the ID of the customer related to the ticket.
     *
     * @param customerID the new customer's ID
     */
    public void setCustomerID(Long customerID) {
        this.customerID = customerID;
    }

    /**
     * Gets the timestamp when the ticket was created.
     *
     * @return the ticket creation timestamp
     */
    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    /**
     * Sets the timestamp when the ticket was created.
     *
     * @param dateCreated the new ticket creation timestamp
     */
    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * Gets the timestamp when the ticket was resolved, or {@code null} if not resolved.
     *
     * @return the ticket resolution timestamp, or {@code null}
     */
    public LocalDateTime getDateResolved() {
        return dateResolved;
    }

    /**
     * Sets the timestamp when the ticket was resolved.
     *
     * @param dateResolved the new ticket resolution timestamp
     */
    public void setDateResolved(LocalDateTime dateResolved) {
        this.dateResolved = dateResolved;
    }

}
