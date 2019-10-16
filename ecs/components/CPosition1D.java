public class CPosition1D implements IComponent {
    CPosition1D(String roomTag) { this.roomTag = roomTag; }

    public final String getComponentName() { return this.getClass().getName(); }

    public String roomTag;
}
