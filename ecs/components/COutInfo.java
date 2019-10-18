public class COutInfo implements IComponent {
    COutInfo(String text) { this.text = text; }

    public final String getComponentName() { return this.getClass().getName(); }

    public String text;
}
