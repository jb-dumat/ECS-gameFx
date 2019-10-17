public class CInDialog implements IComponent {
    CInDialog(Entity target) {
        this.target = target;
        this.text = null;
    }

    public final String getComponentName() { return this.getClass().getName(); }

    public String text;
    public Entity target;
}
