public class CDialog implements IComponent {
    CDialog(String text) { this.text = text; }

    public final String getComponentName() { return this.getClass().getName(); }

    public String text;
}
