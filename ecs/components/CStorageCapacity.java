public class CStorageCapacity implements IComponent {
    CStorageCapacity(int storageCapacity) { this.storageCapacity = storageCapacity; }

    public final String getComponentName() { return this.getClass().getName(); }

    public int storageCapacity;
}
