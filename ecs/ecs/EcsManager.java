import java.util.Collection;

public class EcsManager extends MultiMap<Entity, IComponent> {
    private EcsManager() { super(); }

    public void put(Entity key, IComponent... values) {
        for (IComponent value : values) {
            this.put(key, value);
        }
    }

    public static EcsManager getInstance() {
        if (Instance == null) {
            Instance = new EcsManager();
            return Instance;
        }
        return Instance;
    }

    private static EcsManager Instance;
}
