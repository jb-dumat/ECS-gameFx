/**
 * This component defines the maximum weight
 * an entity can carry
 */
public class CMaxWeight implements IComponent {
    CMaxWeight(String storageCapacity) { this.storageCapacity = Integer.parseInt(storageCapacity); }

    public int storageCapacity;
}
