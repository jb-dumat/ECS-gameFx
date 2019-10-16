public class CDamageHealth implements IComponent {
    CDamageHealth(int damageValue) {
        this.damageValue = damageValue;
    }

    public final String getComponentName() { return this.getClass().getName(); }

    public int getDamageValue() {
        return this.damageValue;
    }

    public void setDamageValue(int damageValue) {
        this.damageValue = damageValue;
    }

    private int damageValue;
}
