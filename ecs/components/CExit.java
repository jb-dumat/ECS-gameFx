public class CExit implements IComponent {
    CExit(String exitTag, String directionTag, Entity exitID)
    {
        this.exitTag = exitTag;
        this.directionTag = directionTag;
        this.exitID = exitID;
    }

    public String getComponentName() { return this.getClass().getName(); }

    public String exitTag;
    public String directionTag;
    public Entity exitID;
}
