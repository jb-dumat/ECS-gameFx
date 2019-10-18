public class CName implements IComponent {
    CName(String name) { this.name = name; }

    public final String getComponentName() { return this.getClass().getName(); }

    public String name;
}
