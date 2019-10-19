public class CInDialog implements IComponent {
    CInDialog() { this.input = ""; }

    public final String getComponentName() { return this.getClass().getName(); }

    public String input;
}
