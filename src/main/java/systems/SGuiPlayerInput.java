package systems;

import core.*;
import ecs.*;
import components.*;
import static java.lang.Thread.sleep;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This system iterates over all entities which
 * contains an InputComponent and open create a
 * command from this input.
 */
public class SGuiPlayerInput {
//    public final int refreshTime
    public static void putInput(String input) {
        EcsManager ecs = EcsManager.getInstance();
        
        ecs.forEachIfContains((e) -> {
                CInPlayer inDialog = (ecs.getComponent(e, CInPlayer.class));
                
                synchronized (inDialog.input) {
                    inDialog.input = input;
                    inDialog.input.notifyAll();
                }
            },
                CInPlayer.class
        );
    }
    
    public static void update() {
        EcsManager ecs = EcsManager.getInstance();

        ecs.forEachIfContains((Entity e) -> {
            CInPlayer inDialog = (ecs.getComponent(e, CInPlayer.class));
            
            synchronized (inDialog.input) {
                try {
                    while (inDialog.input.isEmpty())
                        inDialog.input.wait(100);
                } catch (NoSuchElementException | InterruptedException ex) {
                    GameCore.getInstance().getLogger().warning(ex.getMessage());
                }
            }            
        },
                CInPlayer.class
        );
    }
}
