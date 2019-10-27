public class CComputerLevel implements IComponent {
    enum Level {
        BRAINLESS,
        VERY_EASY,
        EASY,
        MEDIUM,
        HARD,
        VERY_HARD,
        IMPOSSIBLE
    }

    CComputerLevel(Level aiLevel) {
        this.aiLevel = aiLevel;
    }

    public Level aiLevel;
}
