public class Entity {
    Entity() {
        EntityGenerator idGenerator = EntityGenerator.getInstance();
        this.entityID = idGenerator.generateID();
    }

    int getEntityID() {
        return this.entityID;
    }

    private int entityID;
}
