public class CCollision implements IComponent {
    CCollision() {
        this.active = true;
    }

    public final String getComponentName() { return this.getClass().getName(); }

    public boolean active;
}
