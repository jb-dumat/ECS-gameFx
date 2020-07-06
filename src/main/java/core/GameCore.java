package core;

import com.mycompany.mavenproject1.MainApp;
import ecs.EcsManager;
import ecs.SystemManager;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * The GameCore knows how to build a game and
 * how to start it.
 */
public class GameCore extends AGameCore {
    private GameCore() {
        super();
        this.initLogs("log/logs.txt");
        this.ecs = EcsManager.getInstance();
        this.gameBuilder = new GameBuilder(this.ecs);
        this.systemManager = new SystemManager();
    }
    
    /**
     * Initialize the game
     */
    @Override
    public void initializeGame() {
        this.gameBuilder.buildGame();
    }

    public void initializeGameFromFile() {
        MapLoader loader = new MapLoader();
        this.stopGame();
        
        loader.loadGame();
    }
    
    /**
     * Launch the game
     * Add threadpool to support multiple game instances
     */
    @Override
    public void launchGame() {
        this.startThreadPool();
        this.pool.execute(systemManager);       
    }
    
    public void stopGame() {
        systemManager.setStop();
        this.pool.shutdownNow();
    }

    private void startThreadPool() {
        this.pool = Executors.newFixedThreadPool(1);
    }
    
    /**
     * Start the application
     */
    @Override
    public void start() {
        this.initializeGame();
        this.launchGame();
    }

    /**
     * Init the logger
     * @param output the logfile output
     * @return 
     */
    private boolean initLogs(String output) {
        logger = Logger.getLogger("Zul-logger");
        FileHandler fh;

        try {
            fh = new FileHandler(output);  
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();  
            fh.setFormatter(formatter);  
        } catch (SecurityException | IOException e) {
            Logger.getGlobal().severe(e.getMessage());
            return false;
        }
        return true;
    }
    
    /**
     * Logger accessor
     * @return the logger
     */
    public Logger getLogger() {
        return GameCore.logger;
    }
    
    /**
     * Gui accessor
     * @return Gui instance
     */
    public MainApp getGui() {
        return Gui;
    }
    
    /**
     * Instance accessor
     * @return GameCore singleton
     */
    public static GameCore getInstance() {
        if (Instance == null)
            Instance = new GameCore();
        return Instance;
    }
    
    private MainApp Gui;
    private static GameCore Instance;
    private static Logger logger;
    private ExecutorService pool;
}
