import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;

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

    @FunctionalInterface
    public interface ApplySystem {
        void invoke(Entity entity);
    }

    public void forEachIfContains(ApplySystem functor, Class... component) {
        for (Entity entity : this.keySet()) {
            Collection<Class> list = Arrays.asList(component);
            boolean containsAll = false;

            for (IComponent c : this.get(entity)) {
                if (list.contains(c.getClass())) {
                    containsAll = true;
                    break;
                }
            }

            if (containsAll)
                functor.invoke(entity);
        }
    }

    public boolean containsComponent(Collection<IComponent> components, Class component) {
        for (IComponent c : components) {
            if (component.equals(c.getClass())) {
                return true;
            }
        }
        return false;
    }

    public IComponent getComponent(Entity entity, Class component) {
        for (IComponent c : this.get(entity)) {
            if (c.getClass().equals(component)) {
                return c;
            }
        }
        return null;
    }

    public Collection<IComponent> getComponents(Entity entity, Class component) {
        Collection<IComponent> collection = new ArrayList<>();

        for (IComponent c : this.get(entity)) {
            if (c.getClass().equals(component)) {
                collection.add(c);
            }
        }
        return collection;
    }

    public <T> IComponent getComponentByValue(Class component, String fieldName, T value) {
        for (Entity e : this.keySet()) {
            if (this.containsComponent(this.get(e), component)) {
                Collection<IComponent> col = this.getComponents(e, component);

                for (IComponent c : col) {
                    try {
                        Field field = component.getField(fieldName);
                        Object obj = field.get(c);

                        if (obj.equals(value)) {
                            return c;
                        }

                    } catch (NoSuchFieldException | IllegalAccessException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    public <T> Entity getEntityByValue(Class component, String fieldName, T value) {
        for (Entity e : this.keySet()) {
            if (this.containsComponent(this.get(e), component)) {
                Collection<IComponent> col = this.getComponents(e, component);

                for (IComponent c : col) {
                    try {
                        Field field = component.getField(fieldName);
                        Object obj = field.get(c);

                        if (obj.equals(value)) {
                            return e;
                        }

                    } catch (NoSuchFieldException | IllegalAccessException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    public <T> IComponent getEntityComponentByValue(Entity e, Class component, String fieldName, T value) {
        if (this.containsComponent(this.get(e), component)) {
            Collection<IComponent> col = this.getComponents(e, component);

            for (IComponent c : col) {
                try {
                    Field field = component.getField(fieldName);
                    Object obj = field.get(c);

                    if (obj.equals(value)) {
                        return c;
                    }

                } catch (NoSuchFieldException | IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return null;
    }

    public Entity getEntityWith(Class... components) {
        for (Entity entity : this.keySet()) {
            Collection<Class> list = Arrays.asList(components);
            boolean containsAll = false;

            for (IComponent c : this.get(entity)) {
                if (list.contains(c.getClass())) {
                    containsAll = true;
                    break;
                }
            }

            if (containsAll)
                return entity;
        }
        return null;
    }

    private static EcsManager Instance;
}
