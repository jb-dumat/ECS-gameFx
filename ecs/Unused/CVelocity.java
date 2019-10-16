public class CVelocity implements IComponent {
    CVelocity(int velocity) {
        this.velocity = velocity;
    }

    public final String getComponentName() { return this.getClass().getName(); }

    public int velocity;
}
