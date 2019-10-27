/**
 * An abstract class AGameCore.
 * This class implements IGameCore but cannot
 * be built on its own. User must inherit from it.
 * This class defines some fields to be used by the
 * game engine.
 *
 * ecs: The ecs field manages the Entity/Component/System trinity.
 * gameBuilder: This builder is used to build a game.
 * systemManager: Inherits from runnable and runs
 * asynchronously. Defines all the logic
 * of the game.
 */
public abstract class AGameCore implements IGameCore {
    AGameCore() {}

    public abstract void launchGame();

    protected EcsManager ecs;
    protected IGameBuilder gameBuilder;
    protected ISystemManager systemManager;
}
