import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AEcsManager implements ISystemManager {
    AEcsManager() {
        this.componentsPool = new HashMap<String, MultiMap<Entity, IComponent>>();
    }

    public abstract void update();

    public Entity attachComponent(Entity entityObject, IComponent componentObject) {
        String componentName = componentObject.getComponentName();

        if (this.componentsPool.containsKey(componentName)) {
            this.componentsPool.get(componentName).put(entityObject, componentObject);
        } else {
            MultiMap<Entity, IComponent> newKey = new MultiMap<>();
            newKey.put(entityObject, componentObject);
            this.componentsPool.put(componentName, newKey);
        }
        return entityObject;
    }

    public void removeEntity(Entity entityObject) {
        for (Map.Entry<String, MultiMap<Entity, IComponent>> collection : componentsPool.entrySet()) {
            MultiMap<Entity, IComponent> c = collection.getValue();
            c.remove(entityObject);
        }
    }

    public Map<String, MultiMap<Entity, IComponent>> getComponentsPool() {
        return this.componentsPool;
    }

    private Map<String, MultiMap<Entity, IComponent>> componentsPool;
}
