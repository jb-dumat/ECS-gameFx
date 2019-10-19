import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        EcsManager ecs = EcsManager.getInstance();
        SystemManager systemManager = new SystemManager();
        CommandPool cmdPool = CommandPool.getInstance();
        ExecutorService pool = Executors.newFixedThreadPool(1);
        GameManager gameManager = GameManager.getInstance();

        cmdPool.addCommand("go", 1);

        // Generate the game
        gameManager.generateGame();

        Entity player = gameManager.getPlayers().get("player1");

        ((CInDialog) ecs.getComponent(player, CInDialog.class)).input = "go west";

        // Launch Service Loop in ThreadPool
        pool.execute(systemManager);
    }
}
