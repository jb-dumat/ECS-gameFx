public class CExit implements IComponent {
    CExit(String roomTag, String exitTag, String directionTag)
    {
        this.roomTag = roomTag;
        this.exitTag = exitTag;
        this.directionTag = directionTag;
    }

    public String getComponentName() { return this.getClass().getName(); }

    public String roomTag;
    public String exitTag;
    public String directionTag;
}
