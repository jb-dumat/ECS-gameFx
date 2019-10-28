import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The GameCore knows how to build a game and
 * how to start it.
 */
public class CLIGameCore extends AGameCore {
    public CLIGameCore() {
        super();
        this.ecs = EcsManager.getInstance();
        this.pool = Executors.newFixedThreadPool(1);
        this.gameBuilder = new GameBuilder(this.ecs);
        this.systemManager = new SystemManager();
    }

    public void initializeGame() {
        this.gameBuilder.buildGame();
    }

    public void launchGame() {
        this.pool.execute(systemManager);
    }

    private ExecutorService pool;
}
