package hr.java.entity;

import hr.java.enums.AgentStatus;
import hr.java.enums.AgentType;

import java.io.Serializable;
import java.util.Objects;

/**
 * The {@code Agent} class represents an agent entity that extends {@link Person}.
 * It implements {@link Serializable} for object serialization.
 */
public class Agent extends Person implements Serializable {

    private String email;
    private AgentStatus agentStatus;
    private AgentType agentType;

    /**
     * Constructs a new {@code Agent} with the specified details.
     *
     * @param id          the unique identifier of the agent
     * @param firstName   the first name of the agent
     * @param lastName    the last name of the agent
     * @param email       the email address of the agent
     * @param agentStatus the status of the agent (AVAILABLE, BUSY)
     * @param agentType   the type of the agent (AGENT, SUPERAGENT)
     */
    public Agent(Long id, String firstName, String lastName, String email, AgentStatus agentStatus, AgentType agentType) {
        super(id, firstName, lastName);
        this.email = email;
        this.agentStatus = agentStatus;
        this.agentType = agentType;
    }

    /**
     * Gets the email of the agent.
     *
     * @return the agent's email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the agent.
     *
     * @param email the new email address of the agent
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the status of the agent.
     *
     * @return the agent's status
     */
    public AgentStatus getAgentStatus() {
        return agentStatus;
    }

    /**
     * Sets the status of the agent.
     *
     * @param agentStatus the new status of the agent
     */
    public void setAgentStatus(AgentStatus agentStatus) {
        this.agentStatus = agentStatus;
    }

    /**
     * Gets the type of the agent.
     *
     * @return the agent's type
     */
    public AgentType getAgentType() {
        return agentType;
    }

    /**
     * Sets the type of the agent.
     *
     * @param agentType the new type of the agent
     */
    public void setAgentType(AgentType agentType) {
        this.agentType = agentType;
    }

    /**
     * Checks if two agents are equal based on first name, last name, and email.
     *
     * @param o the object to compare
     * @return {@code true} if the agents have the same first name, last name, and email; {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Agent agent = (Agent) o;
        return Objects.equals(getFirstName(), agent.getFirstName()) &&
                Objects.equals(email, agent.email) &&
                Objects.equals(getLastName(), agent.getLastName());
    }

    /**
     * Generates a hash code for the agent based on email and status.
     *
     * @return the hash code of the agent
     */
    @Override
    public int hashCode() {
        return Objects.hash(email, agentStatus);
    }

    /**
     * Returns the string representation of the agent, which is the agent's ID.
     *
     * @return the string representation of the agent
     */
    @Override
    public String toString() {
        return String.valueOf(getId());
    }
}