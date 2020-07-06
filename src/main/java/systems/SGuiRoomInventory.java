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
public class SGuiRoomInventory {
    /**
     * Fetch all output dialog entity and write them
     * on the output stream, then removes it.
     */
    public static void update() {
        FXMLController controller = MainApp.controller;
        EcsManager ecs = EcsManager.getInstance();

        ecs.forEachIfContains((Entity e) -> {
            CPositionString room = ecs.getComponent(e, CPositionString.class);
            Entity roomEntity = ecs.getEntityByComponentValue(CName.class, "name", room.roomTag);
                        
            CInventory inventory = ecs.getComponent(roomEntity, CInventory.class);
            ObservableList<String> items = controller.roomInventory.getItems();
            
            Platform.runLater(() -> {
                items.clear();
                
                for (Entity item : inventory.inventory) {
                    CName itemName = ecs.getComponent(item, CName.class);
                    CWeight itemWeight = ecs.getComponent(item, CWeight.class);
                    
                    items.add(itemName.name + " (" + itemWeight.weight + " kg)");
                }
            });

        },
            CInPlayer.class,
            CPositionString.class);
   }
}
