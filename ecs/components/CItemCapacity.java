public class CItemCapacity implements IComponent {
    CItemCapacity(int itemCapacity) { this.itemCapacity = itemCapacity; }

    public final String getComponentName() { return this.getClass().getName(); }

    public int itemCapacity;
}
