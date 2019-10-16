public class CHealthPoint implements IComponent {
    CHealthPoint(int healthPoint) { this.healthPoint = healthPoint; }

    public final String getComponentName() {
        return this.getClass().getName();
    }

    public int getHealthPoint() {
        return this.healthPoint;
    }

    public void setHealthPoint(int healthPoint) {
        this.healthPoint = healthPoint;
    }

    private int healthPoint;
}
