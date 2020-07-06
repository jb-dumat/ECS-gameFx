package ecs;

/**
 * The ISystem interface
 * A system contains all the logic. It
 * and only it knows ho to update an
 * entity and all its components.
 */
public interface ISystem {
    void update();
}
