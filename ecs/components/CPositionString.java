public class CPositionString implements IComponent {
    CPositionString(String roomTag) { this.roomTag = roomTag; }

    public final String getComponentName() { return this.getClass().getName(); }

    public String roomTag;
}
