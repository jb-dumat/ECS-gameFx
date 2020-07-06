/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systems;

import com.mycompany.mavenproject1.MainApp;
import components.CInPlayer;
import components.CInventory;
import components.CMaxWeight;
import components.CName;
import components.CPositionString;
import core.GameCore;
import ecs.EcsManager;
import javafx.application.Platform;

/**
 *
 * @author JB
 */
public class SGuiNames {
        /**
     * Fetch all output dialog entity and write them
     * on the output stream, then removes it.
     */
    public static void update() {
        EcsManager ecs = EcsManager.getInstance();

        ecs.forEachIfContains((e) -> {
            Platform.runLater(() -> {
                CName playerName = ecs.getComponent(e, CName.class);
                CPositionString roomName = ecs.getComponent(e, CPositionString.class);

                try {
                    MainApp.controller.playerName.setText(playerName.name);
                    MainApp.controller.roomName.setText(roomName.roomTag);
                } catch (Exception ex) {
                    GameCore.getInstance().getLogger().warning(ex.getMessage());
                }
            });
        },
            CInPlayer.class,
            CPositionString.class,
            CInventory.class,
            CMaxWeight.class);
   }
}
