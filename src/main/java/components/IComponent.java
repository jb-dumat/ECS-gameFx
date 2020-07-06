package components;

/**
 * An interface IComponent.
 * A component must implements this interface to be able to
 * be stored and attached as an entity's component by the
 * ecs manager.
 * An entity may hold an "infinite" number of components. Using reflection
 * it is possible to find specific components attached to an entity.
 */
public interface IComponent { }
