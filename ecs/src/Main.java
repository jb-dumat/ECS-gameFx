public class Main {
    public static void main(String[] args) {
        // Create a game core
        IGameCore game = new CLIGameCore();

        // Initialize a game session
        game.initializeGame();

        // Launches a game session
        game.launchGame();
    }
}
