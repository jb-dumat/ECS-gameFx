/**
 * Entity are an abstract representation of the data.
 * They are defined by the component they are holding.
 */
public class Entity {

    /**
     * Construct a new Entity.
     */
    Entity() {
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

    private long entityID;
}
