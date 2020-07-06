package core;

import com.mycompany.mavenproject1.MainApp;
import ecs.*;
import javafx.application.Platform;

/**
 * A CSV Map or default game loader
 */
public class MapLoader {
    public MapLoader() {}
    
    public MapLoader(EcsManager ecs) {}
        
    /**
     * Load a new game
     */
    public void loadGame() {
        EcsManager ecs = EcsManager.getInstance();
        
        Platform.runLater(() -> {
            GameCore.getInstance().stopGame();
            ecs.clear();
            
            if (MainApp.controller.getPathToFile().equals("Default")) {
                GameCore.getInstance().initializeGame();
            } else {
                String path = "./maps/" + MainApp.controller.getPathToFile(); 
                
                GameCore.getInstance().gameBuilder.buildGameFromFile(path);
            }
            GameCore.getInstance().launchGame();
        });
    }
}
