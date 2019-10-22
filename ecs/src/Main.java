import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static ExecutorService pool;

    public static void launchGame() {
        EcsManager ecs = EcsManager.getInstance();
        SystemManager systemManager = new SystemManager();
        CommandPool cmdPool = CommandPool.getInstance();
        pool = Executors.newFixedThreadPool(1);
        GameBuilder gameManager = GameBuilder.getInstance();

        cmdPool.addCommand("go", 1);

        // Generate the game
        gameManager.generateGame();

        // Launch Service Loop in ThreadPool
        pool.execute(systemManager);
    }

    public static void main(String[] args) {
        launchGame();
    }
}
