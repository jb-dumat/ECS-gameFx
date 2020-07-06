package systems;

import com.mycompany.mavenproject1.FXMLController;
import com.mycompany.mavenproject1.MainApp;
import core.*;
import ecs.*;
import components.*;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.collections.ObservableList;

/**
 * The SDialogOutput is a system which provides
 * a way to write an output dialog upon the output
 * stream.
 * If you wish to change the stream, you can make a new
 * stream component and a new system to handle it (A socket
 * communication dialog for example)
 */
public class SGuiRoomPlayers {
    /**
     * Fetch all output dialog entity and write them
     * on the output stream, then removes it.
     */
    public static void update() {
        FXMLController controller = MainApp.controller;
        EcsManager ecs = EcsManager.getInstance();

        ecs.forEachIfContains((Entity e) -> {
            CPositionString room = ecs.getComponent(e, CPositionString.class);                        
            ObservableList<String> players = controller.roomPlayers.getItems();
            
            Platform.runLater(() -> {
                players.clear();
                
                ecs.forEachIfContains((Entity player) -> {
                    CPositionString position = ecs.getComponent(player, CPositionString.class);

                    if (ecs.contains(player, CInPlayer.class))
                        return;
                    if (position.roomTag.equals(room.roomTag)) {
                        CName playerName = ecs.getComponent(player, CName.class);                    
                        players.add(playerName.name);
                    }
                },
                        CPositionString.class);
            });

        },
            CInPlayer.class,
            CPositionString.class);
   }
}
