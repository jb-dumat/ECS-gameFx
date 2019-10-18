public class CInput implements IComponent {
    CInput() {
        this.input = null;
    }

    public final String getComponentName() { return this.getClass().getName(); }

    public String input;
}
