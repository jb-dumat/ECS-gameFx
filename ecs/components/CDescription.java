public class CDescription implements IComponent {
    CDescription(String description) { this.description = description; }

    public final String getComponentName() { return this.getClass().getName(); }

    public String description;
}
