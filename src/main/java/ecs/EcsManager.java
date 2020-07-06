package ecs;

import components.IComponent;
import utils.MultiMap;
import core.GameCore;
import javafx.util.Pair;
import java.lang.reflect.Field;
import java.util.*;

/**
 * The class EcsManager provides a simple API to build games or applications using
 * the Entity/Component/System architectural pattern.
 * It uses reflection to store components as IComponents and deduce their
 * type whenever it is required.
 */
public class EcsManager extends MultiMap<Entity, IComponent> {
    /**
     * Constructor
     */
    private EcsManager() { super(); }

    /**
     * Attaches a collection of components to a new/existing entity.
     * @param key Entity unique ID
     * @param values A collection of IComponents
     */
    public void put(Entity key, IComponent... values) {
        for (IComponent value : values) {
            this.put(key, value);
        }
    }

    /**
     * Get a singleton instance of the EcsManager
     * @return a static instance of the EcsManager
     */
    public static EcsManager getInstance() {
        if (Instance == null) {
            Instance = new EcsManager();
        }
        return Instance;
    }


    /**
     * A functional interface used to apply some logic
     * to a pool of entities
     */
    @FunctionalInterface
    public interface ApplySystem {
        void invoke(Entity entity);
    }

    public <T extends IComponent> boolean contains(Entity e, Class<T> component) {
        return this.get(e).stream().anyMatch((c) -> (c.getClass().equals(component)));
    }
    
    /**
     * Check if the collections holds a component of the requested type
     * @param components A collection of components
     * @param component A requested class type of component to find
     * @return true if the collection contains component, otherwise false
     */
    private <T extends IComponent> boolean containsComponent(Collection<T> components, Class component) {
        return components.stream().anyMatch((c) -> (component.equals(c.getClass())));
    }


    /**
     * Apply a lambda (System logic) to each entity which contains a given pattern of components
     * @param functor logic to apply to an entity
     * @param component The component(s) the entity must contain to be updated
     */
    public void forEachIfContains(ApplySystem functor, Class... component) {
        try {
            for (Entity entity : this.keySet()) {
                int matchingComponent = 0;

                for (IComponent c : this.get(entity)) {
                    for (Class classReq : component) {
                        if (c != null && c.getClass().equals(classReq)) {
                            matchingComponent++;
                            break;
                        }
                    }
                }

                if (component.length == matchingComponent)
                    functor.invoke(entity);
            }
        } catch (Exception ex) {}
    }

    /**
     * Get a collection of all components of the requested type attached to an entity
     * @param <T>
     * @param entity An entity unique ID
     * @param component The component type to get from the entity
     * @return All the components of type Class attached to the entity
     */
    public <T extends IComponent> Collection<T> getComponents(Entity entity, Class<T> component) {
        Collection<T> collection = new ArrayList<>();

        this.get(entity)
                .stream()
                .filter((c) -> (c.getClass().equals(component)))
                .forEachOrdered((c) -> { collection.add((T) c); });
        return collection;
    }

    /**
     * Try to get a single component of the requested type attached to an entity
     * @param <T> Comoponent Type
     * @param entity An entity unique ID
     * @param component The component type to get from the entity
     * @return One component of type Class attached to the entity
     */
    public <T extends IComponent> T getComponent(Entity entity, Class<T> component) {
        for (IComponent c : this.get(entity)) {
            if (c.getClass().equals(component)) {
                return (T)c;
            }
        }
        return null;
    }

    /**
     * Find a component attached to an entity by its value
     * @param <T> The value type
     * @param <C> The component type
     * @param e An unique entity ID
     * @param component The component type to find
     * @param fieldName The fieldName holding the value
     * @param value the value holded by the field
     * @return The component if the value matches, otherwise null
     */
    public <T, C extends IComponent> C getComponentByValue(Entity e, Class<C> component, String fieldName, T value) {
        Pair<Entity, C> pair = this.getEntityComponentByValue(e, component, fieldName, value);

        if (pair == null)
            return null;

        return (C) pair.getValue();
    }

    /**
     * Get an entity
     * @param <T> The value type
     * @param <C> The component type
     * @param component The component type to find
     * @param fieldName The fieldName holding the value
     * @param value the value hold by the field
     * @return The first entity containing the value
     */
    public <T, C extends IComponent> Entity getEntityByComponentValue(Class<C> component, String fieldName, T value) {
        Pair<Entity, C> pair = this.getEntityComponentByValue(component, fieldName, value);

        if (pair == null)
            return null;
        return pair.getKey();
    }


    /**
     * @param <T> The value type
     * @param <C> The component type
     * @param e A unique entity ID
     * @param component The component type to find
     * @param fieldName The component's fieldName holding the value
     * @param value The value to match with the field
     * @return A pair of Entity, IComponent if there is a match, otherwise null
     */
    public <T, C extends IComponent> Pair<Entity, C> getEntityComponentByValue(Entity e, Class<C> component, String fieldName, T value) {
        if (this.containsComponent(this.get(e), component)) {
            for (IComponent c : this.getComponents(e, component) ) {
                try {
                    Field field = component.getField(fieldName);
                    Object obj = field.get(c);

                    if (obj.equals(value)) {
                        return new Pair<>(e, (C)c);
                    }
                } catch (NoSuchFieldException | IllegalAccessException ex) {
                    GameCore.getInstance().getLogger().warning(ex.getMessage());
                }
            }
        }
        return null;
    }

    /**
     * @param <T> The value type
     * @param <C> The component type
     * @param component The component type to find
     * @param fieldName The fieldName holding value
     * @param value The value to match with the fieldName's value
     * @return A pair of entity, component if there is a match, otherwise null
     */
    public <T, C extends IComponent> Pair<Entity, C> getEntityComponentByValue(Class<C> component, String fieldName, T value) {
        for (Entity e : this.keySet()) {
            Pair<Entity, C> pair = this.getEntityComponentByValue(e, component, fieldName, value);

            if (pair != null)
                return pair;
        }
        return null;
    }

    private static EcsManager Instance;
}
