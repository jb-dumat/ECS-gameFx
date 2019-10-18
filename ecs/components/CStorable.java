public class CStorable implements IComponent {
    CStorable(Entity attachedEntity, int weight) {
        this.attachedEntity = attachedEntity;
        this.weight = weight;
    }

    public final String getComponentName() { return this.getClass().getName(); }

    Entity attachedEntity;
    int weight;
}
