/**
 * A weight component
 */
public class CWeight implements IComponent {
    CWeight(String weight) {
        this.weight = Integer.parseInt(weight);
    }

    public int weight;
}
