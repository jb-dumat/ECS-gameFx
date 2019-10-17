public class CExit implements IComponent {
    CExit(String roomTag, String exitTag, String directionTag, Entity exitID)
    {
        this.roomTag = roomTag;
        this.exitTag = exitTag;
        this.directionTag = directionTag;
        this.exitID = exitID;
    }

    public String getComponentName() { return this.getClass().getName(); }

    public String roomTag;
    public String exitTag;
    public String directionTag;
    public Entity exitID;
}
