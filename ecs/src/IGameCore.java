/**
 * An interface IGameCore.
 * This "contract" force the child class to
 * implement a method to initialize a game and another
 * to launch it. The way theses methods are implemented
 * depends of the user.
 */
public interface IGameCore {
    void initializeGame();
    void launchGame();
}
