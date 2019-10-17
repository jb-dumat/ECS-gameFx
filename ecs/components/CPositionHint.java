public class CPositionHint implements IComponent {
    CPositionHint(Entity positionEntity) {
        this.positionEntity = positionEntity;
    }

    public final String getComponentName() { return this.getClass().getName(); }

    Entity positionEntity;
}
