import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void launchGame() {
        EcsManager ecs = EcsManager.getInstance();
        SystemManager systemManager = new SystemManager();
        CommandPool cmdPool = CommandPool.getInstance();
        ExecutorService pool = Executors.newFixedThreadPool(1);
        GameManager gameManager = GameManager.getInstance();

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
