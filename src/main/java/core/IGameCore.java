package core;

/**
 * An interface IGameCore.
 * This "contract" forces the child class to
 * implement a method to initialize a game one to launch it
 * and one that should encapsulates both.
 * The way theses methods are implemented
 * depends of the user.
 */
public interface IGameCore {
    void initializeGame();
    void launchGame();
    void start();
}
