package hr.java.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * The class represents a change made to an entity.
 * It stores details about the change, including the agent who made the change,
 * the entity affected, the field that was changed, and the old and new values of that field.
 * The change also includes a timestamp indicating when the change occurred.
 *
 * @param <T> the type of the entity that is being changed
 */
public class EntityChange<T extends Serializable> implements Serializable
{
    private Agent agent;
    private T entityToChange;
    private String fieldName;
    private String oldValue;
    private String newValue;
    private LocalDateTime time;

    /**
     * Constructs an {@code EntityChange} with the specified details.
     *
     * @param agent        the {@link Agent} who made the change
     * @param entityToChange the entity that was changed
     * @param fieldName    the name of the field that was changed
     * @param oldValue     the old value of the field before the change
     * @param newValue     the new value of the field after the change
     */
    public EntityChange(Agent agent, T entityToChange, String fieldName, String oldValue, String newValue)
    {
        this.agent = agent;
        this.entityToChange = entityToChange;
        this.fieldName = fieldName;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.time = LocalDateTime.now();
    }

    /**
     * Gets agent.
     *
     * @return the agent
     */
    public Agent getAgent()
    {
        return agent;
    }

    /**
     * Sets agent.
     *
     * @param agent the agent
     */
    public void setAgent(Agent agent)
    {
        this.agent = agent;
    }

    /**
     * Gets entity to change.
     *
     * @return the entity to change
     */
    public T getEntityToChange()
    {
        return entityToChange;
    }

    /**
     * Sets entity to change.
     *
     * @param entityToChange the entity to change
     */
    public void setEntityToChange(T entityToChange)
    {
        this.entityToChange = entityToChange;
    }

    /**
     * Gets field name.
     *
     * @return the field name
     */
    public String getFieldName()
    {
        return fieldName;
    }

    /**
     * Sets field name.
     *
     * @param fieldName the field name
     */
    public void setFieldName(String fieldName)
    {
        this.fieldName = fieldName;
    }

    /**
     * Gets old value.
     *
     * @return the old value
     */
    public String getOldValue()
    {
        return oldValue;
    }

    /**
     * Sets old value.
     *
     * @param oldValue the old value
     */
    public void setOldValue(String oldValue)
    {
        this.oldValue = oldValue;
    }

    /**
     * Gets new value.
     *
     * @return the new value
     */
    public String getNewValue()
    {
        return newValue;
    }

    /**
     * Sets new value.
     *
     * @param newValue the new value
     */
    public void setNewValue(String newValue)
    {
        this.newValue = newValue;
    }

    /**
     * Gets time.
     *
     * @return the time
     */
    public LocalDateTime getTime()
    {
        return time;
    }

    /**
     * Sets time.
     *
     * @param time the time
     */
    public void setTime(LocalDateTime time)
    {
        this.time = time;
    }
}
