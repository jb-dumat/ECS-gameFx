public class CAILevel implements IComponent {
    CAILevel(int level) { this.level = level; }

    public final String getComponentName() { return this.getClass().getName(); }

    public int level;
}
