/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems;

import com.mycompany.mavenproject1.FXMLController;
import com.mycompany.mavenproject1.MainApp;
import components.CExit;
import components.CInPlayer;
import components.CName;
import components.CPositionString;
import ecs.EcsManager;
import ecs.Entity;
import java.util.Collection;
import javafx.application.Platform;

/**
 *
 * @author JB
 */
public class SGuiExitBtn {
        /**
     * Fetch all output dialog entity and write them
     * on the output stream, then removes it.
     */
    public static void update() {
        EcsManager ecs = EcsManager.getInstance();

        ecs.forEachIfContains((Entity e) -> {
            FXMLController controller = MainApp.controller;
            CPositionString position = ecs.getComponent(e, CPositionString.class);
            Entity roomEntity = ecs.getEntityByComponentValue(CName.class, "name", position.roomTag);
            Collection<CExit> exits = ecs.getComponents(roomEntity, CExit.class);
            
            Platform.runLater(() -> {
                controller.btnNorth.setDisable(true);
                controller.btnEast.setDisable(true);
                controller.btnWest.setDisable(true);
                controller.btnSouth.setDisable(true);
                
                exits.forEach((exit) -> {
                    switch (exit.directionTag) {
                        case "north": controller.btnNorth.setDisable(false); break;
                        case "south": controller.btnSouth.setDisable(false); break;
                        case "east": controller.btnEast.setDisable(false); break;
                        case "west": controller.btnWest.setDisable(false); break;
                    }
                });
            });
            
        },
                CInPlayer.class,
                CPositionString.class);
   }
}
