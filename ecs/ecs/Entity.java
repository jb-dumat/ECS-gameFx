public class Entity {
    Entity() {
        EntityGenerator idGenerator = EntityGenerator.getInstance();
        this.entityID = idGenerator.generateID();
    }

    long getEntityID() {
        return this.entityID;
    }

    private long entityID;
}
