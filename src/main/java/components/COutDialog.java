package components;

/**
 * A component carrying a text
 * to write on the output stream.
 */
public class COutDialog implements IComponent {
    public COutDialog(String text) { this.text = text; }

    public String text;
}
