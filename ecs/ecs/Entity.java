public class Entity {
    Entity() {
        EntityGenerator idGenerator = EntityGenerator.getInstance();
        this.entityID = idGenerator.generateID();
        this.entityDescription = "";
    }

    int getEntityID() {
        return this.entityID;
    }

    public String getEntityDescription() {
        return entityDescription;
    }

    public void setEntityDescription(String entityDescription) {
        this.entityDescription = entityDescription;
    }

    private int entityID;
    private String entityDescription;
}
