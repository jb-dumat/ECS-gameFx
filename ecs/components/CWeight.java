public class CWeight implements IComponent {
    CWeight(int weight) {
        this.weight = weight;
    }

    public final String getComponentName() { return this.getClass().getName(); }

    public int weight;
}
