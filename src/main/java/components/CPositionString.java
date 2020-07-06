package components;

/**
 * A position component carrying a string
 * of the "position".
 */
public class CPositionString implements IComponent {
    public CPositionString(String roomTag) { this.roomTag = roomTag; }

    public String roomTag;
}
