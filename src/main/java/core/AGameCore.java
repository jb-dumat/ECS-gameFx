package core;

import ecs.EcsManager;
import ecs.ISystemManager;

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
    public AGameCore() {}
    
    @Override
    public abstract void initializeGame();
    
    @Override
    public abstract void launchGame();

    @Override
    public abstract void start();

    protected EcsManager ecs;
    protected ISystemManager systemManager;
    protected IGameBuilder gameBuilder;
}
