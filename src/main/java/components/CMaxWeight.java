package components;

/**
 * This component defines the maximum weight
 * an entity can carry
 */
public class CMaxWeight implements IComponent {
    public CMaxWeight(int storageCapacity) { this.storageCapacity = storageCapacity; }

    public int storageCapacity;
}
