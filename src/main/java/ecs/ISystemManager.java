package ecs;

/**
 * An abstract class that implements Runnable as
 * a proper interface
 */
public abstract class ISystemManager implements Runnable {
    @Override
    public abstract void run();

    public abstract void setStop();
}
