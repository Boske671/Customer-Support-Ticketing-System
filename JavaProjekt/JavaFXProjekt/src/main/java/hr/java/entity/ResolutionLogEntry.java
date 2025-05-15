package hr.java.entity;

import java.time.LocalDateTime;


/**
 * The {@code ResolutionLogEntry} class represents a log entry of a resolution
 * between two entities along with the timestamp when the resolution occurred.
 *
 * @param <T> the type of the first entity
 * @param <U> the type of the second entity
 */
public class ResolutionLogEntry<T, U> {

    private T firstEntity;
    private U secondEntity;
    private LocalDateTime dateTime;

    /**
     * Instantiates a new {@code ResolutionLogEntry} with the specified entities and timestamp.
     *
     * @param firstEntity  the first entity involved in the resolution
     * @param secondEntity the second entity involved in the resolution
     * @param dateTime     the timestamp when the resolution occurred
     */
    public ResolutionLogEntry(T firstEntity, U secondEntity, LocalDateTime dateTime) {
        this.firstEntity = firstEntity;
        this.secondEntity = secondEntity;
        this.dateTime = dateTime;
    }

    /**
     * Gets the first entity involved in the resolution.
     *
     * @return the first entity
     */
    public T getFirstEntity() {
        return firstEntity;
    }

    /**
     * Sets the first entity involved in the resolution.
     *
     * @param firstEntity the first entity to set
     */
    public void setFirstEntity(T firstEntity) {
        this.firstEntity = firstEntity;
    }

    /**
     * Gets the second entity involved in the resolution.
     *
     * @return the second entity
     */
    public U getSecondEntity() {
        return secondEntity;
    }

    /**
     * Sets the second entity involved in the resolution.
     *
     * @param secondEntity the second entity to set
     */
    public void setSecondEntity(U secondEntity) {
        this.secondEntity = secondEntity;
    }

    /**
     * Gets the timestamp of when the resolution occurred.
     *
     * @return the timestamp of the resolution
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Sets the timestamp of when the resolution occurred.
     *
     * @param dateTime the new timestamp to set
     */
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
