public class CPlayerCapacity implements IComponent {
    CPlayerCapacity(int playerCapacity) { this.playerCapacity = playerCapacity; }

    public final String getComponentName() { return this.getClass().getName(); }

    public int playerCapacity;
}
