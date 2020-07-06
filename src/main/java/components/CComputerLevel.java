package components;

/**
 * A computer level component.
 */
public class CComputerLevel implements IComponent {
    public enum Level {
        BRAINLESS,
        VERY_EASY,
        EASY,
        MEDIUM,
        HARD,
        VERY_HARD,
        IMPOSSIBLE
    }

    public CComputerLevel(Level aiLevel) {
        this.aiLevel = aiLevel;
    }

    public Level aiLevel;
}
