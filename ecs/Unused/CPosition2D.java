public class CPosition2D implements IComponent {
    CPosition2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public final String getComponentName() { return this.getClass().getName(); }

    public int x;
    public int y;
}
