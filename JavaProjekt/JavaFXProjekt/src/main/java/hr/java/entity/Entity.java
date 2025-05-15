package hr.java.entity;

import java.io.Serializable;

import java.io.Serializable;

/**
 * The {@code Entity} class serves as an abstract base class for all entities in the system.
 * It provides a unique identifier and implements {@link Serializable} for object serialization.
 */
public abstract class Entity implements Serializable {

    private long id;

    /**
     * Constructs an {@code Entity} with the specified ID.
     * If the provided ID is {@code null}, it defaults to {@code 0L}.
     *
     * @param id the unique identifier of the entity, may be {@code null}
     */
    protected Entity(Long id) {
        this.id = (id != null) ? id : 0L;
    }

    /**
     * Gets the unique identifier of the entity.
     *
     * @return the entity's ID
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the entity.
     *
     * @param id the new ID of the entity
     */
    public void setId(long id) {
        this.id = id;
    }
}
