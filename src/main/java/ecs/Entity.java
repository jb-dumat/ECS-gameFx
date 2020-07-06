package ecs;

/**
 * Entity are an abstract representation of a game object.
 * They are defined by the component they are holding.
 */
public class Entity {

    /**
     * Construct a new Entity.
     */
    public Entity() {
        EntityGenerator idGenerator = EntityGenerator.getInstance();
        this.entityID = idGenerator.generateID();
    }

    /**
     * Getter to access the integer hold by the entity.
     * Mainly used for debug cases.
     * @return The entityID field
     */
    long getEntityID() {
        return this.entityID;
    }

    // The field Entity ID is used for Debug purpose
    private final long entityID;
}
